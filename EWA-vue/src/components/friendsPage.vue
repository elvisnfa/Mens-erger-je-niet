<template>
<!--  <NavBar></NavBar>-->
  <div class="friend-template">
    <div class="card">
      <h1>Friends</h1>
      <div class="friends-page">
        <div class="friends-list-container">
          <div class="friends-list">
            <div class="searchBar">
              <input v-model.lazy="searchInput" v-on:keyup.enter="this.userFound()" type="text" placeholder="Search for friends..."/>
              <button @click="this.userFound()" class="chat-button">Search</button>
            </div>

            <ul v-if="searchInput === ''" class="friend-items">
              <li v-for="friend in filteredFriends" :key="friend.id" class="friend-item">
                <div
                    v-bind:class="{'friend-card': this.selectedFriend !== friend, 'friend-card2': this.selectedFriend === friend}">
                  <span class="friend-name">{{ friend.username }}</span>
                  <div class="friend-actions">
                    <button class="chat-button" @click="startChat(friend)">Chat</button>
                    <button class="invite-button" @click="inviteFriend(friend.userId)">Invite</button>
                  </div>
                </div>
              </li>
            </ul>
            <ul v-else class="friend-items">
              <li v-for="user in searchedUser" :key="user.id" class="friend-item">
                <div class="friend-card">
                  <span class="friend-name">{{ user.username }}</span>
                  <div v-if="friends.some(friend => friend.username === user.username) &&
                  user.username !== this.currentUser.username" class="friend-actions">
                    <button class="chat-button" @click="startChat(user)">Chat</button>
                    <button class="invite-button" @click="inviteFriend(user.userId)">Invite</button>
                  </div>
                  <div v-else-if="user.username !== this.currentUser.username" class="friend-actions">
                    <button class="invite-button" @click="addFriend(user.userId)">Add</button>
                  </div>
                </div>
              </li>
            </ul>

          </div>
        </div>
        <div class="chatbox-container">
          <router-view :friend="this.selectedFriend"></router-view>
        </div>
      </div>

    </div>
    <div class="background"></div>
  </div>
</template>

<script>
// import NavBar from "@/components/NavBar";
// import chatBox from "@/components/chat/chatBox";

export default {
  name: 'friendsPage',
  // components: {NavBar},
  inject: ['SessionService', 'chatFriend'],
  data() {
    return {
      friends: [],
      searchInput: '',
      selectedFriend: null,
      searchedUser: [],
      currentUser: null,
    };
  },
  created() {
    console.log(this.selectedFriend)
    this.reinitializeFriends();
    this.currentUser = this.SessionService.currentAccount;


  },
  computed: {
    filteredFriends() {
      const searchTerm = this.searchInput.toLowerCase();
      if (!searchTerm.length > 0) {
        return this.friends;
      }
      return null;
    },
  },
  methods: {
    startChat(friend) {
      // Logic to start a chat with the friend
      console.log('Starting chat with friend:', friend);
      if (this.selectedFriend === friend) {
        this.selectedFriend = null;
      } else {
        this.selectedFriend = friend;
        this.$router.push(this.$route.matched[0].path + "/" + friend.userId)
      }


    },
    inviteFriend(friendId) {
      // Logic to invite the friend
      console.log('Inviting friend:', friendId);
    },
    reinitializeFriends() {
      this.chatFriend.findAll().then((array) => {
        this.friends = array;
      });
    },
    userFound() {
      this.chatFriend.searchUser(this.searchInput).then((array) => {
        this.searchedUser = array;
      });
    },
    addFriend(userId) {
      this.chatFriend.addFriend(userId).then(() => {
        this.reinitializeFriends();
      });
      this.searchInput = '';
    }

  },
  watch: {
    $route(to, from) {
      // Perform actions when the route changes
      // Call any methods or perform any logic based on the route change
      // For example, you can update the selectedFriend based on the route params
      if (this.$route.params.id !== undefined) {
        let friendId = parseInt(this.$route.params.id);

        this.selectedFriend = this.friends.find(friend => {
          return friend.userId === friendId;
        });

        console.log('Selected friend:', this.selectedFriend);
      }

    },
    'selectedFriend'() {
      if (this.selectedFriend === null) {
        this.$router.push("/friends");
      }
    }
  }
};
</script>

<style scoped>

h1 {
  text-align: center;
}

.friend-template {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 30px;
}

.friends-page {
  display: flex;
  justify-content: center;
}

input {
  width: 100%;
}

.searchBar {
  display: flex;
}

.friends-list-container {
  width: 300px;
  padding: 20px;
  border-radius: 4px;
}

.chatbox-container {
  padding: 20px;
  margin-left: 10px;
}

.background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
  background-image: url("../assets/Ludobackground.png");
  background-color: rgba(5, 11, 98, 1);
  background-size: cover;
  background-position: center;
  filter: brightness(0.5)
}

/*.friends-list-container {*/
/*  background-color: #f1f1f1;*/
/*}*/
.friends-list {
  width: 300px;
  padding: 20px;
  border-radius: 4px;
  background-color: #fff;
}

.friend-items {
  list-style: none;

  padding: 0;
}

.friend-item {
  margin-bottom: 10px;
  background-color: #f1f1f1;
}

.friend-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.friend-card2 {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #66bb6a;
}

.friend-name {
  font-size: 16px;
  font-weight: bold;
}

.friend-actions {
  display: flex;
  align-items: center;
}

.chat-button {
  margin-right: 10px;
  padding: 6px 10px;
  background-color: #3772ff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.invite-button {
  padding: 6px 10px;
  background-color: #3772ff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>

