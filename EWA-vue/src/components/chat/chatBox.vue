<template>
  <div class="chatbox">
    <div class="chat-messages" ref="messageContainer">
      <div v-for="message in messages" :key="message.id" class="message"
           :class="{ 'float-right': message.fromUser.userId === userId }">
        <div v-if="message.fromUser.userId !== userId" class="message-left">
          <span class="message-sender">{{ message.fromUser.username }}: </span>
          <span class="message-text">{{ message.message }}</span>
        </div>
        <div v-else class="message-right">
          <span class="message-text">{{ message.message }}</span>
        </div>
      </div>
    </div>
    <div class="chat-input">
      <input v-model="userInput" v-on:keyup.enter="onNewMessage" type="text" placeholder="Type a message..."/>
      <button @click="onNewMessage">Send</button>
    </div>
  </div>
</template>

<script>

export default {
  name: "chatBox",
  props: ["friend"],
  inject: ['notificationService', 'SessionService', 'chatFriend' ],
  data() {
    return {
      messages: [],
      userInput: "",
      userId: null,
      friendId: null,
      chatLink: null,
    };
  },
  mounted() {
    this.scrollToBottom();
  },
  updated() {
    this.scrollToBottom();
  },
  created() {
    if (this.friend === null){
      this.$router.push("/friends")
    }
    this.userId = this.SessionService.currentAccount.userId;
    this.createChatLink();


  },

  methods: {
    createChatLink() {
      this.friendId = this.$route.params.id;
      if (this.userId > this.friendId) {
        this.chatLink = this.friendId + "&" + this.userId;
      } else {
        this.chatLink = this.userId + "&" + this.friendId;
      }
      this.notificationService.subscribe("chat" + this.chatLink, this.reInitialize)
      this.reInitialize();
    },
    onNewMessage() {
      if (this.userInput !== "") {

        this.$nextTick(() => {
          this.scrollToBottom();
        });

        // this method is called when enter is pressed within the input text field
        // for demo purpose of a simple web socket

        this.chatFriend.sendMessage(this.userInput, this.friendId);

        this.userInput = "";
        // a persistent announcement system would save the announcement here via the REST api
        // and let the rest controller issue the websocket notification to inform all clients about the update
      }
    },

    async reInitialize() {
      // reload all books from the back-end
      this.messages = (await this.chatFriend.asyncFindChatWithFriend(this.friendId));
    },

    scrollToBottom() {
      const container = this.$refs.messageContainer;
      container.scrollTop = container.scrollHeight;
    }
  },
  watch: {
    $route(to, from) {
      // Perform actions when the route changes
      console.log('Route changed:', to, from);

      // Call any methods or perform any logic based on the route change
      // For example, you can update the selectedFriend based on the route params
      this.createChatLink();
    }
  }
}
</script>

<style scoped>
.friend-bar {
  width: 200px;
  height: 10px;
  background-color: #3772ff;
  padding-bottom: 20px;
}

.chatbox {
  position: relative;
  width: 400px;
  height: 300px;
  margin: 0 auto;
  background-color: #f1f1f1;
  padding: 20px;
  border-radius: 4px;
  overflow: hidden;
}

.chat-messages {
  max-height: calc(100% - 70px); /* Adjusted to account for the chat input and close button heights */
  overflow-y: auto;
}

.message {
  margin-bottom: 10px;
  clear: both;
}

.float-right {
  float: right;
}

.message-left {
  background-color: #3772ff;
  color: #fff;
  padding: 8px;
  border-radius: 8px;
  word-wrap: break-word;
  float: left;
}

.message-right {
  background-color: #66bb6a;
  color: #fff;
  padding: 8px;
  border-radius: 8px;
  word-wrap: break-word;
}

.message-sender {
  font-weight: bold;
  margin-bottom: 4px;
}

.chat-input {
  position: absolute;
  bottom: 50px; /* Adjusted to leave space for the close button */
  left: 20px;
  right: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

input[type="text"] {
  flex: 1;
  padding: 8px;
  border: none;
  border-radius: 4px;
}

button {
  padding: 8px 16px;
  background-color: #3772ff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}


</style>
