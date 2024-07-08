<template>
  <NavBar></NavBar>

  <div class="leaderboard">
    <router-link to="/Dashboard"><img src="../assets/back.png" class="back-button"></router-link>
    <h1 class="leaderboard-heading">Leaderboard</h1>
    <div class="leaderboard-table">
<!--      <v-text-field-->
<!--          label="Lobby code"-->
<!--          type="text"-->
<!--          v-model="username"-->
<!--      ></v-text-field>-->
<!--      <button class="btn btn-primary btn-sm play" @click="Joingame(username)">&#9658;</button>-->
      <table>
        <thead>
        <tr>
          <th>Ranking</th>
          <th>Username</th>
          <th>Points</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(user,index) in even(users)" :key="user">
          <td>{{ index + 1 }}</td>
          <td>{{ user.username }}</td>
          <td>{{ user.points }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>

  <div class="background">
    <img src="../assets/Ludobackground.png">
  </div>


</template>

<script>
import NavBar from "@/components/NavBar.vue";
export default {
  name: "LeaderboardPage",
  components: {NavBar},
  inject: ['leaderboardService'],
  data() {
    return {
      users: [],
    }
  },
  async created() {
    this.users = await this.leaderboardService.asyncFindAll();

    // // Temporary hardcoded amount of wins
    // for (let i = 0; i < this.users.length; i++) {
    //   this.users[i].points = Math.round(Math.random() * 1000);
    // }
  },
  methods: {
    // Sort from most wins to least wins
    even: function(arr) {
      return arr.slice().sort(function(a, b) {
        return b.points - a.points;
      });
    },
  }
}
</script>

<style scoped>
.back-button {
  height: 30px;
  width: 30px;
}
.leaderboard {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.15);
  padding: 1rem;
  margin: 50px auto 0;
  max-width: 600px;
  text-align: center;
}

.leaderboard-heading {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.leaderboard-table {
  overflow-x: auto;
}

table {
  border-collapse: collapse;
  width: 100%;
  margin-top: 1rem;
}

th, td {
  padding: 0.75rem;
  text-align: left;
}

th {
  font-weight: bold;
  background-color: #f7f7f7;
}

tbody tr:nth-child(even) {
  background-color: #f7f7f7;
}

.leaderboard-ranking {
  font-weight: bold;
}

.leaderboard-avatar img {
  border-radius: 50%;
  display: block;
  margin: 0 auto;
}

.leaderboard-username {
  display: flex;
  align-items: center;
}

.leaderboard-username i {
  margin-right: 0.5rem;
}

.leaderboard-wins, .leaderboard-coins {
  font-weight: bold;
}

@media screen and (max-width: 600px) {
  table {
    font-size: 0.8rem;
  }
}




.background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1000;
  background-color: rgba(5, 11, 98, 1);
}

.background img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: bottom;
  filter: brightness(0.7);
}

</style>
