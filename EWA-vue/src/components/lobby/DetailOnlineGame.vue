<template>
  <div class="body">
    <h1>Online Game</h1>
    <div class="players">
      <p>Aantal spelers</p>
      <p>{{ players }}</p>
      <button class="ai" @click="maxPlayer()" v-on:click="players--">-</button>
      <button class="ai" @click="maxPlayer()" v-on:click="players++">+</button>
    </div>
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
    </div>

    <button class="start" @click="createLobby">Create lobby</button>


  </div>
</template>

<script>


import {Lobby} from "@/models/Lobby";
// import {isDisabled} from "bootstrap/js/src/util";

export default {
  name: "DetailOnlineGame",
  inject: ['userService', 'lobbyService'],
  props: ['availableColors'],
  data() {
    return {
      players: 1,
      selectedColor: null,
      user: null,
      userId: null,
    }
  },
  methods: {
    // isDisabled,
    maxPlayer() {
      if (this.players < 1) {
        return this.players = 2;
      }
      if (this.players > 3) {
        return this.players = 2;
      }
    },

    colorChoosing(color) {
      this.selectedColor = color;
    },

    async createLobby() {
      if (this.selectedColor !== null && this.players > 1) {
        this.user = await this.userService.asyncGetInfo();
        this.userId = this.user.userId;
        const newlobby = Lobby.createLobby(this.selectedColor, 0, this.players, this.players, this.userId);
        await this.lobbyService.asyncSave(newlobby);
        const createdLobby = await this.lobbyService.asyncFindByjoincode(newlobby.join_code);
        await this.lobbyService.updatePlayerCount(createdLobby[0].idLobby);
        await this.lobbyService.combineUserWithLobby(this.userId, createdLobby[0].idLobby,this.selectedColor);
        this.$router.push("/lobby/" + createdLobby[0].join_code)
      } else {
        alert("je bent iets vergeten");
      }
    }
  }
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

.players {
  background-color: white;
  text-align: center;
  margin: 10px;
  border: solid blue;
  border-radius: 12px;
}

.playerColor {
  background-color: white;
  text-align: center;
  margin: 10px;
  border: solid blue;
  border-radius: 12px;
}

.ai {
  background: dodgerblue;
  border-radius: 5px;
  border: 2px solid;
  padding-left: 5px;
  padding-right: 5px;
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