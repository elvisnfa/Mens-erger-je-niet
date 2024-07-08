<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <img src="../assets/Hvalogo.png" alt="Image could not be loaded">
      </div>
    </div>
  </div>
  <div class="jumbotron">
    <div class="container">
      <div class="row mt-4">
        <div class="col-md-12">
          <div class="card shadow-lg">
            <div class="card-body">
              <h1 class="display-4">Welcome to Hva Ludo!</h1>
              <h4 class="lead"> Roll the dice, make your move, and win the game!</h4>
              <hr class="my-4">
              <router-link to="/Loginpage" class="btn btn-dark btn-lg shadow-sm">
                Login
              </router-link>
              <router-link to="/Registerpage" class="btn btn-dark btn-lg shadow-sm">
                Register here
              </router-link>
            </div>
          </div>
        </div>
      </div>
      <div id="second-row" class="row">
        <div class="col-md-12">
          <div class="card shadow-lg">
            <div class="card-body">
              <div class="row">
                <div class="col-md-6">
                  <h2>About the site</h2>
                  <p class="lead">Our website offers a virtual version of Ludo that you can play online with friends or against other players from around the world.
                    You can customize the game settings, such as the number of players and the difficulty level,
                    to make the game more challenging and exciting Register to enjoy all
                    functionalities!
                    Also when registered, don't forget to log in!
                  </p>
                </div>
                <div class="col-md-6">
                  <h2>Ludo Board</h2>
                  <p class="lead">With a simple and user-friendly interface, our Ludo game is easy to navigate and enjoyable to play.
                    You can chat with other players, share your achievements, and track your progress on our leaderboard.
                    So why wait? Join the fun and start playing Ludo on our website today! </p>
                </div>
              </div>
              <div class="row">
                <div id="ludo-board" class="col-md-12">
                  <hr>
                  <img class="ludo-board" src="../assets/ludo-board.jpg" alt="Image could not be loaded">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="background">
    </div>

  </div>

</template>

<script>


export default {
  name: "homePage",
}
</script>

<style scoped>

.btn{
  margin-left: 7px;
}
.jumbotron {
  background-size: cover;
  height: 100%;
}

#second-row {
  margin-top: 75px;
}

p {
  font-size: 20px;
}

.ludo-board {

  width: 10%;
  margin: 10px;
}

.card {
  border-radius: 10px;
}


.background, .background img {
  width: 100vw;
  height: 100vh;
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