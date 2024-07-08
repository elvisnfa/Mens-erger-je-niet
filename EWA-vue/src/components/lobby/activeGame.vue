<template>
  <NavBar></NavBar>
  <div class="wrapper">
    <main class="page-main">
      <div class="container flex-column">
        <router-link to="/Dashboard"><img src="../../assets/back.png" class="back-button"></router-link>
        <table class="table">
          <thead>
          <tr>
            <th scope="col">GameID</th>
            <th scope="col">Creator</th>
            <th scope="col">Participants</th>
            <th scope="col">Join game</th>
            >
          </tr>
          </thead>
          <tbody>

          <tr v-for="(game) in this.games" :key="game">
            <td>{{ game.idLobby }}</td>
            <td>{{game.creatorNames}}</td>
            <td>{{ game.player_size + "/" + game.max_allowed_Players }}</td>
            <td>
              <button class="btn btn-primary btn-lg" @click="this.joinGame(game)" >&#9658;</button>
            </td>
          </tr>
          </tbody>
        </table>
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
  name: "activeGame.vue",
  components: {NavBar},
  inject: ['lobbyService', 'userService'],
  data(){
    return {
      games: [],
      lobbyCreators: [],
      currentUser: [],
      creatorNames: [],
    }
  },
  async created() {
    this.currentUser = await this.userService.asyncGetInfo();
    this.games = await this.lobbyService.asyncFindAllLobbyForPLayer(this.currentUser.userId);

    for (let i = 0; i < this.games.length; i++) {
      let user = await this.userService.asyncFindId(this.games[i].userid_owner);
      this.games[i].creatorNames = user.username;
    }
  },
  methods: {
    joinGame(lobby){
      if (lobby.lobby_status === 0){
        this.$router.push("/lobby/" + lobby.join_code)
      }
      else{
        this.$router.push("/gamepage/" + lobby.join_code)
      }
    }
  }
}
</script>

<style scoped>
.back-button {
  height: 30px;
  width: 30px;
  margin-bottom: 10px;
}

.page-main {
  flex-grow: 1;
}

.container {
  display: flex;
  background-color: white;
  padding: 3%;
  margin-top: 7%;
  border-radius: 10px;
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

/*.lobbyContent {*/
/*  display: flex;*/
/*  justify-content: center;*/
/*  margin-top: 36%;*/
/*  padding: 20px;*/
/*}*/

/*.lobbyDetail {*/
/*  display: none; !* Hidden by default *!*/
/*  position: fixed; !* Stay in place *!*/
/*  left: 0;*/
/*  top: 0;*/
/*  height: 100%; !* Full height *!*/
/*  overflow: auto; !* Enable scroll if needed *!*/
/*  background-color: rgba(0, 0, 0, 0.4); !* Black w/ opacity *!*/
/*  width: 100%;*/
/*  border-radius: 20px;*/
/*}*/

.playerColor {
  background-color: white;
  text-align: center;
  margin: 10px;
  border: solid blue;
  border-radius: 12px;
}

.playerColorButton {
  border-radius: 5px;
  border: 2px solid;
  padding-left: 5px;
  padding-right: 5px;
  margin: 5px;
}

.playerColorButton.disabled {
  opacity: 0.5; /* Reduce the opacity of disabled buttons */
}


.active {
  background-color: black;
  color: white;
}

.inactive {
  background-color: grey;
  color: white;
}

/*.close {*/
/*  color: red;*/
/*  float: right;*/
/*  font-size: 50px;*/
/*  font-weight: bold;*/
/*}*/

</style>