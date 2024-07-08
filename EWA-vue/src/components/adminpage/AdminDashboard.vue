<template>
  <NavBar></NavBar>

  <div class="screen">

  <div class="card-container">


    <div class="card">
      <div class="input">
        <v-banner>
          <div class="container">
            <div class="row">
              <h3>Welkom</h3>
              <p>Hieronder staan een paar uitgelichte gegevens!</p>
            </div>
          </div>
        </v-banner>
      </div>

      <h1 class="header-statistics ">Statistieken</h1>
      <div class="col-4">
        <div class="background-chart">
          <p class="new_users">{{ this.amountUser }}</p>
          <div class="new_users_span">
            <span style="color: white;">Geregistreerde Gebruikers</span>
          </div>
        </div>
      </div>

    </div>

      <div class="card2">
          <router-link class="button-list" to="AdminUserList">
          <v-btn color="indigo-darken-4" id="#change-username" ref="change-username">User-Lists</v-btn>
          </router-link>
          <router-link class="button-list" to="/">
          <v-btn color="indigo-darken-4" >Lobby-Lists</v-btn>
          </router-link>
      </div>


    <div class="background">
      <img src="../adminpage/Ludobackground.png">
    </div>
   </div>
  </div>

</template>

<script>
import NavBar from "@/components/NavBar.vue";

export default {
  name: "AdminDashboard",
  inject: ['userService'],
  components: {NavBar},

  data() {
    return {
      users: [],
      amountUser: null,
    }
  },
  async created() {
    this.users = await this.userService.asyncFindAll();
    this.amountUser = this.users.length;
    console.log(this.amountUser.length);
  }


}
</script>

<style scoped>

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

.screen {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.card {
  display: flex;
  flex-direction: column;
  margin-top: 50px;
  align-items: center;
  background: white;
  width: 50%;

}

.card-container {
    display: flex;
    justify-content: space-between;
    width: 50%;
    margin-top: 50px;
}


.header-statistics {
  margin: 1pc 0pc 3pc 0pc;
}

.background-chart {
  background-color: #050B62FF;
  padding: 5pc 1pc 5pc 1pc;
  border-radius: 1pc;
}

.background-chart:hover {
  background-color: #0911af;
}

.new_users {
  font-size: 4pc;
  color: white;
  font-weight: bold;
  text-align: center;
  border: 7px solid white;
  border-radius: 4pc;
  width: 7pc;
  margin: 0 auto;
}

.new_users_span {
  color: white;
  text-align: center;
}

.card2 {
    /* Add the following styles */
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background: white;
    width: 30%;
    margin-top: 50px;
    padding: 5px;
}

.button-list {
    width: 100%;
    margin-bottom: 10px;
    text-align: center;
}

</style>