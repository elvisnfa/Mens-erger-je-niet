<template>
<!--  <NavBar></NavBar>-->
  <div class="wrapper">
    <main class="page-main">
      <div class="container flex-column">
        <router-link to="/Dashboard"><img src="../../assets/back.png" class="back-button"></router-link>
        <v-text-field
            label="Lobby code"
            type="text"
            v-model="join_code"
        ></v-text-field>
        <popup class="popup" v-if="showPopup1">
          <div class="lobbyDetail" :style="{display: showPopup1 ? 'block' : 'none'}">
            <div class="lobbyContent">
              <span @click="showPopup1 = !showPopup1" class="close">&times;</span>
              <div class="playerColor">
                <p>Choose a starting color:</p>
                <button class="playerColorButton" :class="{ active: selectedColor === 'red'}"
                        id="playerRed" @click="colorChoosing('red')">Red
                </button>
                <button class="playerColorButton" :class="{ active: selectedColor === 'blue'}"
                        id="playerBlue" @click="colorChoosing('blue')">Blue
                </button>
                <button class="playerColorButton" :class="{ active: selectedColor === 'yellow'}"
                        id="playerYellow" @click="colorChoosing('yellow')">Yellow
                </button>
                <button class="playerColorButton" :class="{ active: selectedColor === 'green'}"
                        id="playerGreen" @click="colorChoosing('green')">Green
                </button>
                <button class="btn btn-primary btn-sm play" @click="Joingame(join_code)">&#9658;</button>
              </div>
            </div>
          </div>
        </popup>
        <button class="btn btn-primary btn-lg" @click="showPopup1 = !showPopup1">Join a lobby using a code</button>
        <!--        <button class="btn btn-info" @click="Joingame(this.join_code)">Join a lobby using a code</button>-->
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
          <tr v-for="(game) in games" :key="game">
            <td>{{ game.idLobby }}</td>
            <td>{{ game.creatorNames }}</td>
            <td>{{ game.player_size + "/" + game.max_allowed_Players }}</td>
            <td>
              <button class="btn btn-primary btn-lg" @click="disableColors(game, game.join_code)">&#9658;</button>
            </td>
            <popup class="popup" v-if="game.showPopup2">
              <div class="lobbyDetail" :style="{display: game.showPopup2 ? 'block' : 'none'}">
                <div class="lobbyContent">
                  <span @click="game.showPopup2 = !game.showPopup2" class="close">&times;</span>
                  <div class="playerColor">
                    <p>Choose a starting color:</p>
                    <button class="playerColorButton"
                            :class="{ active: selectedColor === 'red', inactive: redColorSelectionDisabled}"
                            id="red" @click="colorChoosing('red')" :disabled="redColorSelectionDisabled">Red
                    </button>
                    <button class="playerColorButton"
                            :class="{ active: selectedColor === 'blue', inactive: blueColorSelectionDisabled}"
                            id="blue" @click="colorChoosing('blue')" :disabled="blueColorSelectionDisabled">Blue
                    </button>
                    <button class="playerColorButton"
                            :class="{ active: selectedColor === 'yellow', inactive: yellowColorSelectionDisabled}"
                            id="yellow" @click="colorChoosing('yellow')" :disabled="yellowColorSelectionDisabled">Yellow
                    </button>
                    <button class="playerColorButton"
                            :class="{ active: selectedColor === 'green', inactive: greenColorSelectionDisabled}"
                            id="green" @click="colorChoosing('green')" :disabled="greenColorSelectionDisabled">Green
                    </button>
                  </div>
                </div>
              </div>
              <button class="btn btn-primary btn-sm play" @click="Joingame(game.join_code)">&#9658;</button>
            </popup>
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
// import NavBar from "@/components/NavBar.vue";
// import {isDisabled} from "bootstrap/js/src/util";
// import {el} from "vuetify/locale";
// import DetailJoinGame from "@/components/lobby/DetailJoinGame.vue";


export default {

  name: "ActiveGamesScreen",
  // components: {NavBar},
  inject: ['lobbyService', 'userService'],

  data() {
    return {
      allgames: [],
      creatorNames: [],
      lobbyCreators: [],
      user: null,
      userId: null,
      mygames: [],
      games: [],
      join_code: "",
      selectedColor: null,
      lobby: null,

      users: [],
      lobbyMax: null,
      playerCount: null,
      selectedColorsinLobby: [],
      redColorSelectionDisabled: false,
      blueColorSelectionDisabled: false,
      greenColorSelectionDisabled: false,
      yellowColorSelectionDisabled: false,
      showPopup1: false,
      showPopup2: false,
    }
  },
  async created() {
    this.allgames = await this.lobbyService.asyncFindAll();
    this.user = await this.userService.asyncGetInfo();
    this.userId = this.user.userId;
    console.log(this.allgames)
    console.log(this.selectedColorsinLobby)


    // This is to avoid seeing games that you made yourself, and it will only show the games that did not start yet.
    for (let i = 0; i < this.allgames.length; i++) {
      if (this.allgames[i].lobby_status === 0) {
        if (this.allgames[i].userid_owner === this.userId) {
          this.mygames.push(this.allgames[i]);
        } else {
          this.games.push(this.allgames[i]);
        }
      }
    }

    //this is to find the username of all the lobby owners, so it will show in the lobby browser.
    for (let i = 0; i < this.games.length; i++) {
      if (this.games[i].userid_owner !== 0 && this.games[i].userid_owner !== null) {
        this.lobbyCreators.push(await this.userService.asyncFindId(this.games[i].userid_owner))
      } else {
        this.lobbyCreators.push("Bugged user")
      }
    }

    //saves the creator name in games variable.
    for (let i = 0; i < this.allgames.length; i++) {
      this.games[i].creatorNames = this.lobbyCreators[i].username
    }
  },

  methods: {

    // isDisabled,
    colorChoosing(color) {
      this.selectedColor = color;
    },

    //Method to disable colors that have already been picked by other players.
    async disableColors(game, join_code) {
      game.showPopup2 = !game.showPopup2;

      // Enable all buttons
      this.redColorSelectionDisabled = false;
      this.blueColorSelectionDisabled = false;
      this.yellowColorSelectionDisabled = false;
      this.greenColorSelectionDisabled = false;

      const createdLobby = await this.lobbyService.asyncFindByjoincode(join_code);
      this.selectedColorsinLobby = await this.lobbyService.asyncFindColorToLobby(createdLobby[0].idLobby)

      if (this.selectedColorsinLobby.includes("red")) {
        this.redColorSelectionDisabled = true;
      }
      if (this.selectedColorsinLobby.includes("blue")) {
        this.blueColorSelectionDisabled = true;
      }
      if (this.selectedColorsinLobby.includes("yellow")) {
        this.yellowColorSelectionDisabled = true;
      }
      if (this.selectedColorsinLobby.includes("green")) {
        this.greenColorSelectionDisabled = true;
      }

    },

    //Method to show the separate game popups.
    togglePopup(game) {
      // this.disableColors(game.join_code);

      game.showPopup2 = !game.showPopup2;
    },

    //Async method to join a game using a join code of that lobby
    async Joingame(join_code) {
      //saves the response and send it to the User_has_lobby.

      this.playerCount = await this.lobbyService.asyncFindMaxPlayerCountCompare(join_code);
      this.lobbyMax = await this.lobbyService.asyncFindMaxPlayer(join_code);
      console.log(this.lobbyMax);
      console.log(this.playerCount);
      if (this.lobbyMax <= this.playerCount) {
        alert('max players')
      } else {
        //dummy data to test combination between user and lobby
        //this has to be removed if the selectColor pop-up is implemented
        const selectedColor = this.selectedColor;

        const createdLobby = await this.lobbyService.asyncFindByjoincode(join_code);

        // Link the user to the lobby
        await this.lobbyService.combineUserWithLobby(this.userId, createdLobby[0].idLobby, selectedColor);
        await this.lobbyService.updatePlayerCount(createdLobby[0].idLobby);

        //Push router to lobby with join code, so it will see it in the params
        this.$router.push("/lobby/" + createdLobby[0].join_code)
      }

    },

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
