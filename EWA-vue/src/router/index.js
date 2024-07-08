import { createRouter, createWebHashHistory } from 'vue-router'
import LoginPage from "@/components/LoginPage.vue";
import Ludoscreen from "@/components/Ludoscreen.vue";
import Dashboard from "@/components/Dashboard.vue";
import rulePage from "@/components/rulePage.vue";
import RegisterScreen from "@/components/RegisterScreen.vue";
import homePage from "@/components/homePage.vue";
import UserPage from "@/components/userpage/Userpage.vue";
import settingsPage from "@/components/settingsPage.vue";
import detailLobby from "@/components/lobby/DetailLobby.vue";
import chatBox from "@/components/chat/chatBox.vue";
import LeaderboardPage from "@/components/LeaderboardPage.vue";
import AdminDashboard from "@/components/adminpage/AdminDashboard.vue";
import AdminUserList from "@/components/adminpage/AdminUsersList.vue";
import JoinGame from "@/components/lobby/JoinGame.vue";
import HostGame from "@/components/lobby/HostGame.vue";
import friendsPage from "@/components/friendsPage.vue";
import activeGame from "@/components/lobby/activeGame.vue";

const routes = [
    {path: '/homePage', component: homePage},
    {path: '/Loginpage', component: LoginPage},
    {path: '/gamepage/:joincode', component: Ludoscreen},
    {path: '/gamepage/', redirect: '/gamepage/:joincode'},
    {path: '/Dashboard', component: Dashboard},
    {path: '/', redirect: '/homePage'},
    {path: '/UserPage', component: UserPage},
    {path: '/rulepage', component: rulePage},
    {path: '/Registerpage', component: RegisterScreen},
    {path: '/settings', component: settingsPage},
    {path: '/joingame', component: JoinGame},
    {path:'/activeGames', component: activeGame},
    {path: '/hostgame', component: HostGame},
    {path: '/LeaderboardPage', component: LeaderboardPage},
    {path: '/adminPage', component: AdminDashboard},
    {path: '/AdminUserList', component: AdminUserList},
    {path:'/lobby/:joincode', component: detailLobby},
    {path:'/chatBox',component: chatBox},
    {path:'/friends', component: friendsPage,
        children: [{path: ':id', component: chatBox}]},



];

export const router = createRouter({
  history: createWebHashHistory(),
  routes
})

