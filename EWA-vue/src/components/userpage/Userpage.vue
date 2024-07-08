<template>
  <NavBar></NavBar>
  <div class="profile-card">
    <div class="profile-buttons">
      <button class="delete-button" @click="onDelete">Delete Account</button>
      <button class="edit-button" @click="toggleEditing" :disabled="firstNameError || lastNameError || emailError">
        Edit profile
      </button>
    </div>
    <div class="profile-avatar">
      <img src="@/assets/icon.png">
    </div>
    <div class="profile-info">
      <h1 class="profile-username">{{ username }}</h1>
      <p v-if="!editing" class="profile-fullname">{{ firstName }} {{ lastName }}</p>
      <div v-if="editing">
        <input class="profile-firstname editing" v-model="firstName" :class="{ error: firstNameError }"
               @change="onFirstNameChange"/>
        <input class="profile-lastname editing" v-model="lastName" :class="{ error: lastNameError }"
               @change="onLastNameChange"/>
<!--        <div v-if="firstNameError || lastNameError" class="error-message">{{ firstNameError || lastNameError }}</div>-->
      </div>
      <p v-if="!editing" class="profile-email">{{ email }}</p>
      <input v-else class="profile-email editing" v-model="email" :class="{ error: emailError }"
             @change="onEmailChange"/>
      <div v-if="emailError" class="error-message">{{ emailError }}</div>
      <div class="profile-stats">
        <div class="profile-wins">
          <p class="stat-label">Wins</p>
          <p class="stat-value">10</p>
        </div>
        <div class="profile-coins">
          <p class="stat-label">Coins</p>
          <p class="stat-value">200</p>
        </div>
      </div>
      <button class="save-button" @click="onUpdate"  :disabled="!editing"
              :class="{ 'opacity-100': editing, 'opacity-50': !editing }">
        Save
      </button>
    </div>
  </div>
  <div class="background">
    <img src="../userpage/Ludobackground.png">
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import {toast} from "vue3-toastify";

export default {
  name: "UserPage",
  components: {NavBar},
  inject: ['userService', 'SessionService'],
  data() {
    return {
      firstName: null,
      lastName: null,
      email: null,
      editing: false,
      emailError: null,
      fullNameError: null,
      username: null,
      user: null,
      userId: null
    };
  },

  async created() {
    this.user = await this.userService.asyncGetInfo();
    this.userId = this.user.userId;

    if (this.user !== null) {
      this.username = this.user.username;
      this.email = this.user.email;
      this.firstName = this.user.firstname;
      this.lastName = this.user.lastname;
    }
  },
  methods: {
    toggleEditing() {
      this.editing = !this.editing;


    },
    onEmailChange() {
      this.emailError = null;
    },
    onFullNameChange() {
      this.fullNameError = null;
    },

    async onDelete() {
      const confirmationMessage = confirm("Are you sure you want to delete your account?")

      if (confirmationMessage === true) {
        try {
          await this.userService.asyncDeleteById(this.userId);
          toast.success("Account successfully deleted!")
          this.SessionService.signOut();
        } catch (e) {
          console.log(e);
          toast.error("Account couldn't be deleted, please check the console.")
        }
      }
    },

    async onUpdate() {
      this.editing = !this.editing;
      this.user.firstname = this.firstName;
      this.user.lastname = this.lastName;
      this.user.username = this.username;
      this.user.email = this.email;

      await this.userService.asyncUpdate(this.user)
      toast.success("Account succesfully edited!");
    },
  },

  watch: {
    email: function () {
      // this.emailValidation();
    },
    fullName: function () {
      // this.fullNameValidation();
    },
    name: "UserPage",
    components: {NavBar},
  }
}
</script>

<style scoped>

.profile-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.15);
  padding: 1rem;
  margin: 50px auto 0;
  max-width: 600px;
  text-align: center;
}

.profile-avatar {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  overflow: hidden;
  margin-bottom: 1rem;
}

.profile-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.profile-info {
  flex: 1;
  margin-bottom: 1rem;
  margin-top: 1rem;
}

.profile-username {
  font-size: 2rem;
  margin-bottom: 2rem;
}

.profile-fullname {
  font-size: 1rem;
  font-weight: bold;
  margin-bottom: 0.5rem;

}

.profile-email {
  font-size: 1rem;
  margin-bottom: 1rem;
}

.profile-stats {
  display: flex;
  flex-direction: row;
  margin-bottom: 2rem;
  margin-top: 2rem;
  justify-content: center;
}

.profile-wins,
.profile-coins {
  display: flex;
  flex-direction: column;
  margin: 1rem;
}

.stat-label {
  font-size: 1rem;
  font-weight: bold;
  margin-bottom: 0.25rem;
}

.stat-value {
  font-size: 1.5rem;
}

/**
Editing css
 */

.edit-button {
  display: flex;
  align-self: flex-start;
  background-color: blue;
  color: white;
  border: none;
  padding: 5px 10px;
  margin-left: 10rem;
  margin-bottom: 2rem;
  border-radius: 10px;
  cursor: pointer;
}

.delete-button {
  display: flex;
  align-self: flex-end;
  background-color: red;
  color: white;
  border: none;
  padding: 5px 10px;
  margin-right: 10rem;
  margin-bottom: 2rem;
  border-radius: 10px;
  cursor: pointer;
}

.save-button {
  background-color: blue;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 10px;
  cursor: pointer;
}

.opacity-100 {
  opacity: 1.0;
}

.opacity-50 {
  background-color: white;
}

.profile-buttons {
  display: flex;
  flex-direction: row;
}

.delete-button:hover {
  background-color: lightcoral;
}

.editing {
  border: 2px solid yellow;
  background-color: #ffffcc;
  margin: 1rem;
}

.edit-button:hover {
  background-color: #0065cc;
}

.error {
  border-color: red;
}

.error-message {
  color: red;
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