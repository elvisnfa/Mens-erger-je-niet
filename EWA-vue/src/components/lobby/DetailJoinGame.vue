<template>
  <div class="body">
    <h1>Join Game</h1>
    <div class="playerColor" v-for="(game) in games" :key="game">
      <p>Choose a starting color:</p>
      <button class="playerColorButton" :class="{ active: selectedColor === 'red'}"
              id="red" @click="colorChoosing('red')" :disabled="colorSelectionDisabled">Red
      </button>
      <button class="playerColorButton" :class="{ active: selectedColor === 'blue'}"
              id="blue" @click="colorChoosing('blue')" :disabled="colorSelectionDisabled">Blue
      </button>
      <button class="playerColorButton" :class="{ active: selectedColor === 'yellow'}"
              id="yellow" @click="colorChoosing('yellow')" :disabled="colorSelectionDisabled">Yellow
      </button>
      <button class="playerColorButton" :class="{ active: selectedColor === 'green'}"
              id="green" @click="colorChoosing('green')" :disabled="colorSelectionDisabled">Green
      </button>
    </div>

    <button class="start" @click="createLobby">Join lobby</button>


  </div>

</template>

<script>

import {Lobby} from "@/models/Lobby";
import {isDisabled} from "bootstrap/js/src/util";


export default {
  name: "DetailJoinGame",
  inject: ['userService', 'lobbyService'],
  props: ['availableColors'],
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
      selectedColorsinLobby: [],
      colorSelectionDisabled: false,
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
    isDisabled,

    colorChoosing(color) {
      this.selectedColor = color;
    }
  },

  //Async method to join a game using a join code of that lobby
  async Joingame(join_code) {
    //saves the response and send it to the User_has_lobby.

    //dummy data to test combination between user and lobby
    //this has to be removed if the selectColor pop-up is implemented
    const selectedColor = this.selectedColor;

    const createdLobby = await this.lobbyService.asyncFindByjoincode(join_code);
    this.selectedColorsinLobby = await this.lobbyService.asyncFindColorToLobby(createdLobby[0].idLobby)
    for (let i = 0; i < this.selectedColorsinLobby.length; i++) {
      if (this.selectedColorsinLobby[i] === selectedColor){
        this.colorSelectionDisabled = true;
        alert("color has already been selected")
        break;
      } else {
        await this.lobbyService.combineUserWithLobby(this.userId, createdLobby[0].idLobby, selectedColor);
        //Push router to lobby with join code, so it will see it in the params
        this.$router.push("/lobby/" + createdLobby[0].join_code)
      }

    }
  },
}
</script>

<style scoped>
.body {
  position: fixed;
  top: 25%;
  left: 25%;
  width: 50%;
  height: 50%;
  border-radius: 20px;
  text-align: center;
  background: dodgerblue;
  border: solid blue;
  display: flex;
  flex-direction: column;
  justify-content: center;
  z-index: 9999; /* ensure the overlay is on top of other elements */
}

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


#playerRed:hover {
  background-color: red;
}

#playerBlue:hover {
  background-color: blue;
}

#playerYellow:hover {
  background-color: yellow;
}

#playerGreen:hover {
  background-color: green;
}

.active {
  background-color: black;
  color: white;
}

.start {
  background-color: #002B7F;
  color: white;
  border: solid blue;
  padding: 5px;
}
</style>