<template>
  <div class="login-form card">
    <div class="title">
      <v-banner>
        <h2>Register</h2>
      </v-banner>
    </div>
    <form v-on:submit.prevent="createUser()">
      <div class="px-3 pt-3">
        <div class="row mb-2">
          <div class="col">
            <v-text-field
                label="Username"
                type="text"
                @keyup="checkUsername()"
                v-model="username"
            ></v-text-field>
            <label v-if="this.usernameValidated"
                   class="checkLabel">
              Gelieve minimaal 4 characters bestaande uit letters of cijfers gebruiken.
            </label>
          </div>
        </div>
        <div class="row mb-2">
          <div class="col">
            <v-text-field
                label="Email address"
                type="email"
                v-model="email"
            ></v-text-field>
          </div>
        </div>
          <div class="row mb-2">
          <div class="col">
            <v-text-field
                label="firstname"
                type="text"
                @keyup="checkFirstname"
                v-model="firstname"
            ></v-text-field>
            <label v-if="this.firstnameValidated"
                   class="checkLabel">
              Gelieve minimaal 4 characters bestaande uit letters gebruiken.
            </label>
          </div>
        </div>
          <div class="row mb-2">
          <div class="col">
            <v-text-field
                label="lastname"
                type="text"
                @keyup="checkLastname"
                v-model="lastname"
            ></v-text-field>
            <label v-if="this.lastnameValidated"
                   class="checkLabel">
              Gelieve minimaal 4 characters bestaande uit letters gebruiken.
            </label>
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
        <v-btn @click="createUser()" type="submit" variant="flat" color="indigo-darken-4" class="w-100" style="transition-duration: 0.5s;">
          Submit
        </v-btn>
      </div>
    </div>
    <div class="register">Already Have an Account?
      <router-link to="/Loginpage" class="link-primary fw-bold">Login</router-link>
    </div>
  </div>

  <div class="background">
  <img src="../assets/Ludobackground.png">
  </div>
</template>

<script>
import UserRepository from "@/reposetory/UserRepository";
import User from "@/models/user";
import {toast} from "vue3-toastify";

//two regex's to validate input and check if its valid
let usernameRegex = new RegExp("^(?=.*[a-z]*)(?=.*[A-Z]*)(?=.*[0-9]*)(?=.{4,})");


export default {
  name: "RegisterScreen",
  inject: ['userService', 'loginService','SessionService'],
  data() {
    return {
      UserRepository: new UserRepository(),
      username: '',
      email: '',
      firstname: '',
      lastname: '',
      password: null,
      usernameValidated: false,
      usernameErrorMessage: '',
      firstnameValidated: false,
      lastnameValidated: false,
      passwordValidated: false,
    };
  },
  methods: {
    async createUser() {
      try {
        if (!this.usernameValidated && !this.firstnameValidated && !this.lastnameValidated ) {
            const newuser = User.createUser(this.username,this.firstname,this.lastname,this.email,this.password);
            await this.loginService.asyncSave(newuser);

            //log in the new user
            await this.SessionService.asyncSignIn(this.username,this.password);
            this.$router.push("/Dashboard");
        } else {
            toast.error("Error encouterd check your input");
        }
      } catch (e) {
        console.log(e);
        toast.error("User not Created");
      }
    },
    checkUsername() {
        this.usernameValidated = !usernameRegex.test(this.username);
    },
    checkFirstname() {
      this.firstnameValidated = !usernameRegex.test(this.firstname);
    },
    checkLastname() {
      this.lastnameValidated = !usernameRegex.test(this.lastname);
    }
  }
}
</script>

<style scoped>
.title{
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

.register{
  text-align: center;
}

label {
  color: indianred;
}


</style>