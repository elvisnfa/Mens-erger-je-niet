<template>
  <header>
          <nav>
              <div class="logo">
                  <router-link to="/Dashboard"><a href="#"><img src="../assets/Hvalogo.png" height="70" alt="Hva logo"></a></router-link>
              </div>
            <h3 class="logo">Hva Ludo King</h3>
              <ul>
                  <router-link to="/LeaderboardPage"><li><a href="#">Leaderboard</a></li></router-link>
                  <router-link to="/friends"><li><a href="#">Friends</a></li></router-link>
<!--                  <router-link to="/adminPage" v-if="isAdmin"><li><a href="#">Admin</a></li></router-link>-->

              </ul>
              <img src="../assets/icon.png" class="user-pic" @click="toggleMenu()">

              <div ref="subMenu" class="sub-menu-wrap" id="subMenu">
                  <div class="sub-menu">
                      <div class="user-info">
                          <img src="../assets/icon.png">
                          <h3> {{checkPage}}</h3>
                      </div>
                      <hr>

                      <router-link to="UserPage" style="text-decoration: none">
                          <a href="#" class="sub-menu-link">
                              <img src="../assets/profile.png">
                              <p>Edit Profile</p>
                              <span> > </span>
                          </a>
                      </router-link>


                      <router-link to="settings" style="text-decoration: none">
                          <a href="#" class="sub-menu-link">
                              <img src="../assets/setting.png">
                              <p>Settings</p>
                              <span> > </span>
                          </a>
                      </router-link>

                      <router-link to="/rulePage" style="text-decoration: none">
                          <a href="#" class="sub-menu-link">
                              <img src="../assets/help.png">
                              <p>Rules</p>
                              <span> > </span>
                          </a>
                      </router-link>

                      <router-link to="/LoginPage" style="text-decoration: none">
                          <a  v-on:click="handleLogout()" href="#" class="sub-menu-link">
                              <img src="../assets/logout.png">
                              <p>Logout</p>
                              <span> > </span>
                          </a>
                      </router-link>


                  </div>
              </div>

          </nav>
  </header>

</template>

<script>
export default {
  name: "NavBar.vue",
  props: ["isLoggedIn"],
  inject: ['SessionService'],
  data(){
    return {
        drawer: null,
    }
  },

  methods: {
    handleLogout(){
      this.SessionService.signOut();
    },
      toggleMenu(){
          this.$refs.subMenu.classList.toggle("open-menu");
      }
  },
  computed: {
    checkPage(){
      if (this.isLoggedIn === false){
        return "Log in"
      }
      // else{
        // if (this.SessionService.currentAccount != null){
        //   return this.SessionService.currentAccount.username.toString();
        // }
        // return ""
      // }
    },
    // isAdmin(){
    //     return this.SessionService.isAdmin();
    // }

  }
}
</script>

<style scoped>

*{
    margin: 0;
    padding: 0;
    font-family: sans-serif;
    box-sizing: border-box;
}

nav{
    width: 100%;
    padding: 15px 5%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;
    background-color: #333;
}

.logo {
    /*width: 300px;*/
    /*color: white;*/
    font-size: 24px;
    font-weight: bold;
    color: #fff;
    display: flex;
    align-items: center;
}

.logo img {
  height: 70px;
  margin-right: 10px;
}

.user-pic{
    width: 40px;
    border-radius: 50%;
    cursor: pointer;
    margin-left: 30px;
}

.user-pic:hover {
  background-color: #fff;
}

nav ul{
    width: 100%;
    text-align: right;
}
nav ul li{
    display: inline-block;
    list-style: none;
    margin: 10px 20px;
}
nav ul li a{
    color: white;
    position: relative;
    text-decoration: none;
}

nav ul li a:hover{
    /*color: #ffffff;*/
    color: #fff;
    border-bottom: 2px solid #fff;
}

nav ul li a:hover::after {
    content: '';
    position: absolute;
    left: 0;
    bottom: -5px;
    width: 100%;
    height: 2px;
    background-color: #ffffff;
}

.sub-menu-wrap{
    position: absolute;
    top: 100%;
    right: 5%;
    width: 275px;
    max-height: 0px;
    overflow: hidden;
    transition: max-height 0.5s, opacity 0.5s;
    opacity: 0;
}
.sub-menu-wrap.open-menu{
    max-height: 400px;
    opacity: 1;
}

.sub-menu{
    background: white;
    padding: 20px;
    margin: 10px;
    border-radius: 10px;
}
.user-info{
    display: flex;
    align-items: center;
}
.user-info h3{
    font-weight: bold;
}
.user-info img{
    width: 60px;
    border-radius: 50%;
    margin-right: 15px;
}
.sub-menu hr{
    border: 0;
    height: 2px;
    width: 100%;
    background: #ccc;
    margin: 15px 0 10px;
}
.sub-menu-link{
    display: flex;
    align-items: center;
    text-decoration: none;
    color: #525252;
    margin: 12px 0;
}
.sub-menu-link p{
    width: 100%;
}
.sub-menu-link img{
    width: 40px;
    background: #e5e5e5;
    border-radius: 50%;
    padding: 8px;
    margin-right: 15px;
}
.sub-menu-link span{
    font-size: 22px;
    transition: transform 0.5s;
}
.sub-menu-link:hover span{
    transform: translate(5px);
}
.sub-menu-link:hover p{
    font-weight: 600;
}


</style>
