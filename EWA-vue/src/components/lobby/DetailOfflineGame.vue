<template>
  <div class="body">
    <h1>Offline Game</h1>
    <div class="players">
      <p>AI bots:</p>
      <p>{{ players }}</p>
      <button class="ai" @click="maxPlayer()" v-on:click="players= 0">-</button>
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


    <router-link :to="{ path: '/gamepage/', query: { selectedColor: selectedColor } }"
                 v-if="players >= 1 && selectedColor !== null">
      <p></p>
      <button class="start">Start game</button>
    </router-link>

    <p v-else>Player must select a color <br> There has to be a minimal of 1 AI bot</p>

  </div>

</template>

<script>
export default {
  name: "DetailOfflineGame",
  inject: ['userService', 'SessionService'],
  data() {
    return {
      players: 0,
      selectedColor: null
    }
  },
  methods: {
    maxPlayer() {
      if (this.players < 0) {
        return this.players = 1;
      }
      if (this.players > 3) {
        return this.players = 2;
      }
    },

    colorChoosing(color) {
      this.selectedColor = color;
      console.log(process.env.VUE_APP_API_URL)
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