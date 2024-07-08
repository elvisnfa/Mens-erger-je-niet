<template>
  <NavBar></NavBar>
  <div class="wrapper">
    <main class="page-main">
      <div class="container flex-column">
        <router-link to="/Dashboard"><img src="../../assets/back.png" class="back-button"></router-link>

        <h1>Active game {{ this.lobbyCode }}</h1>

        <h4>host : {{ this.host.username }} </h4>

        <table class="table">
          <thead>
          <tr>
            <th scope="col">Username</th>
            <th scope="col">Points</th>
            <th scope="col">SelectedColor</th>
          </tr>
          </thead>
          <tbody>

          <tr v-for="user in this.users" :key="user.userId">
            <td>{{ user.username }}</td>
            <td>{{ user.points }}</td>
            <td>{{ user.selectedColor }}</td>
          </tr>
          </tbody>
        </table>
        <button class="btn btn-primary" :disabled="!hasChanged" @click="startGame()">Start game</button>
      </div>
    </main>
  </div>

  <div class="background">
    <img src="../../assets/Ludobackground.png">
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";


export default {
  name: "DetailLobby",
  components: {NavBar},
  inject: ['lobbyService','userService','notificationService'],
  data() {
    return {
      lobbyCode: null,
      lobby: null,
      userids: [],
      users: [],
      host: null,
      isOwner: false,

      //for ownership to start the game.
      user: null,
      myId: null,
    }
  },
  async created() {
    //get current user to decide if he gets ownership of the lobby
    this.user = await this.userService.asyncGetInfo();
    this.myId = this.user.userId;

    //get the lobby code from route param and finds associated lobby
    this.lobbyCode = this.$route.params.joincode;
    this.lobby = await this.lobbyService.asyncFindByjoincode(this.lobbyCode);

    const ownerid = this.lobby[0].userid_owner;
    this.host = await this.userService.asyncFindId(ownerid);

    console.log(this.lobby[0].idLobby)
    this.notificationService.subscribe(this.lobby[0].idLobby, this.reInitialize)

    this.reInitialize();
    if (this.lobby[0].lobby_status === 1 || this.lobby[0].lobby_status === 2){
      //redirect to game with lobbycode.
      this.$router.push("/gamepage/"+ this.lobbyCode);
    }


    if (this.myId === ownerid) {
      this.isOwner = true;
    }else{
      this.notificationService.subscribe(this.lobbyCode, this.startGame)
    }
  },

  methods: {
    async reInitialize(){
      // Find all user id's connected to the lobby
      this.userids = await this.lobbyService.asyncFindAllConnectedToLobby(this.lobby[0].idLobby);

      this.users = [];
      for (let i = 0; i < this.userids.length; i++) {
        //saves users in users variable and searches connected color in the many-to-many table
        this.users.push(await this.userService.asyncFindId(this.userids[i]));
        const returnStatement =
            await this.lobbyService.asyncFindColorConnectedToUser(this.lobby[0].idLobby,this.users[i].userId);
        this.users[i].selectedColor = returnStatement[0];
      }
    },
    async startGame() {
      //changes status to 1 which is the status for active game.
      if (this.isOwner){
        this.lobby[0].lobby_status = 1;
        await this.lobbyService.asyncUpdate(this.lobby[0]);
        this.notificationService.notify(this.lobbyCode);
      }


      //redirect to game with lobbycode.
      this.$router.push("/gamepage/"+ this.lobbyCode);
    }
  },

  computed: {
    hasChanged() {
      return this.isOwner;
    },
  }
}
</script>

<style scoped>
.back-button {
  height: 30px;
  width: 30px;
}

.page-main {
  flex-grow: 1;
}

.container {
  display: flex;
  background-color: white;
  padding: 3%;
  margin-top: 7%;
}

.delete, .play {
  border-radius: 50%;
  text-align: center;
}

.play {
  padding-left: 10px;
}

.background, .background img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: bottom;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1000;
  filter: brightness(0.7);
  background-color: rgba(5, 11, 98, 1);
}
</style>