<template>
<!--  <NavBar></NavBar>-->
  <div class="screen">

    <div class="card">
      <router-link to="/Dashboard"><img src="../../assets/back.png" class="back-button"></router-link>

      <div class="card-header">
        <h1 class="title">Create a game</h1>
        <p>Choose to host an offline or online game:</p>
      </div>


      <div class="card-buttons">

        <button class="btn btn-primary btn-lg" @click="showPopup1 = !showPopup1">Host Offline Game</button>
        <popup class="popup" v-if="showPopup1">
          <div class="lobbyDetail" :style="{display: showPopup1 ? 'block' : 'none'}">
            <div class="lobbyContent">
              <span @click="showPopup1 = !showPopup1" class="close">&times;</span>
              <DetailOfflineGame></DetailOfflineGame>
            </div>
          </div>
        </popup>


        <button class="btn btn-primary btn-lg" @click="showPopup2 = !showPopup2">Host Online Game</button>
        <popup class="popup" v-if="showPopup2">
          <div class="lobbyDetail" :style="{display: showPopup2 ? 'block' : 'none'}">
            <div class="lobbyContent">
              <span @click="showPopup2 = !showPopup2" class="close">&times;</span>
              <DetailOnlineGame></DetailOnlineGame>
            </div>
          </div>
        </popup>
      </div>
    </div>

  </div>

  <div class="background">
    <img src="../../assets/Ludobackground.png">
  </div>

</template>

<script>
import DetailOnlineGame from "@/components/lobby/DetailOnlineGame.vue";
import DetailOfflineGame from "@/components/lobby/DetailOfflineGame.vue";
import NavBar from "@/components/NavBar.vue";
import LobbyRepo from "@/reposetory/lobbyRepo";

export default {
  name: "DashboardScreen",
  components: {NavBar, DetailOnlineGame, DetailOfflineGame},
  data() {
    return {
      lobbyRepo: new LobbyRepo(),
      showPopup1: false,
      showPopup2: false,
    }
  },
}
</script>


<style scoped>

.back-button {
  height: 30px;
  width: 30px;
  position: absolute;
  top: 10px;
  left: 10px;
}

.screen {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 80vh;
}

.card {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 30px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 10px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
  width: 50%;
  min-width: 350px;
  z-index: 1;
}

.card-header {
  text-align: center;
  border-color: black;
  margin-bottom: 20px;
}

.lobbyContent {
  display: flex;
  justify-content: center;
  margin-top: 36%;
  padding: 20px;
  /*width: 20%; !* Could be more or less, depending on screen size *!*/
}

.lobbyDetail {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  left: 0;
  top: 0;
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
  width: 100%;
  border-radius: 20px;
}

.close {
  color: red;
  float: right;
  font-size: 50px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
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


input {
  background: #F2F2F2;
}

.btn {
  /*margin-left: 7px;*/
  /*margin-bottom: 30px;*/
  margin-left: 7px;
  margin-bottom: 30px;
  font-size: 1.2rem;
  padding: 10px 30px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: #fff;
  transition: all 0.2s ease-in-out;
  cursor: pointer;
}

.btn:hover {
  background-color: #0069d9;
}

.btn:active {
  background-color: #005cbf;
}

.card-buttons {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 3%;
  gap: 20px;
}

.title {
  display: flex;
  justify-content: center;
}

</style>