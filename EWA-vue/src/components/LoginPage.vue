<template>

  <div class="login-form card">
    <div class="title">
      <v-banner>
        <h2>Login</h2>
      </v-banner>
    </div>
    <form>
      <div class="px-3 pt-3">
        <div class="row mb-2">
          <div class="col">
            <v-text-field
                label="Username"
                type="text"
                v-model="username"
            ></v-text-field>
          </div>
        </div>
        <div class="row mb-2">
          <div class="col">
            <v-text-field
                label="Password"
                type="password"
                hint="Enter your password to access this website"
                v-model="password"
            ></v-text-field>
          </div>
        </div>
      </div>
    </form>
    <div class="row px-3 pb-3">
      <div class="col">
        <v-btn @click="loginRedirect" type="submit" variant="flat" color="indigo-darken-4" class="w-100"
               style="transition-duration: 0.5s;">
          Submit
        </v-btn>
      </div>
    </div>
    <div class="register">New Here?
      <router-link to="/Registerpage" class="link-primary fw-bold">Create an Account</router-link>
    </div>
  </div>

  <div class="background">
    <img src="../assets/Ludobackground.png">
  </div>
</template>

<script>
import {toast} from "vue3-toastify";

export default {
  name: "LoginPage",
  inject: ['SessionService'],
  data() {
    return {
      email: '',
      password: '',
      username: '',
    };
  },

  computed: {
    hasChanged() {
      if (this.password !== '' && this.username !== '') {
        return true;
      } else {
        return false;
      }
    }
  },

  methods: {
    async loginRedirect() {
      let account = await this.SessionService.asyncSignIn(this.username,this.password);
      if (account == null) {
        console.log("Wrong credentials")
        toast.error("Wrong credentials")
      } else {
        this.$router.push("/Dashboard");
        toast.success("Login successful");
      }
    }
  }
}

</script>

<style scoped>
.title {
  text-align: center;
}

.login-form {
  width: 90vw;
  max-width: 400px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
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

.register {
  text-align: center;
}

</style>
