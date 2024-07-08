<template>
  <router-view></router-view>
</template>

<script>
import {UserAdaptor} from "@/adaptors/UserAdaptor";
import {AuthenticationAdaptor} from "@/adaptors/AuthenticationAdaptor";
import {shallowReactive} from "vue";
import {SessionService} from "@/adaptors/SessionService";
import {FetchInterceptor} from "@/adaptors/FetchInterceptor";
import {toast} from "vue3-toastify";
import {LeaderboardAdaptor} from "@/adaptors/LeaderboardAdaptor";
import {LobbyAdaptor} from "@/adaptors/LobbyAdaptor";
import {NotificationAdaptor} from "@/adaptors/NotificationAdapter";
import {ChatFriendAdaptor} from "@/adaptors/Chat&FriendAdaptor";
import {LudoAdaptor} from "@/adaptors/LudoAdaptor";
import {diceAdaptor} from "@/adaptors/diceAdaptor";

export default {
  name: 'App',
  components: {},
  provide() {
    const URL = process.env.VUE_APP_API_URL;

    this.theSessionService = shallowReactive(
        new SessionService(URL + "/authentication", "JWT_LUDO"));

    this.theFetchInterceptor =
        new FetchInterceptor(this.theSessionService, this.$router, toast);

    this.NotificationService = new NotificationAdaptor(URL +"/notifications");


    return {
      userService: new UserAdaptor(URL + "/users"),
      loginService: new AuthenticationAdaptor(URL + "/authentication"),
      leaderboardService: new LeaderboardAdaptor(URL + "/leaderboard"),
      lobbyService: new LobbyAdaptor(URL + "/Lobby"),
      chatFriend: new ChatFriendAdaptor(URL),
      ludoService: new LudoAdaptor(URL + "/Ludo"),
      diceService: new diceAdaptor(URL + "/Dice"),

      SessionService: this.theSessionService,
      // service for notification framework
      notificationService: this.NotificationService,

    }
  },
  beforeUnmount() {
    this.theFetchInterceptor.unregister();
    this.NotificationService.disconnect();
  }
}

</script>

<style scoped>

</style>