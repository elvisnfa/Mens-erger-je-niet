import {SessionService} from "@/adaptors/SessionService";

export class ChatFriendAdaptor {
    RESOURCE_URL;
    sessionAccount;

    constructor(resourceUrl) {
        this.RESOURCE_URL = resourceUrl;
        this.sessionAccount = JSON.parse(window.sessionStorage.getItem("JWT_LUDO" + "_ACC"));


    }

    async fetchJSon(url, options = null) {
        let response = await fetch(url, options)
        if (response.ok) {
            return await response.json();
        } else {
            // response body provides the http-error information
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    async asyncFindAll(){
        let response =  await fetch (this.RESOURCE_URL + "/chat/getAll", {
            headers: {'Content-Type': 'application/json'},
            method: 'GET',
        })
        if (response.ok){
            return await response.json();
        }
    }

    async asyncFindChatWithFriend(friendId){
        let response =  await fetch (this.RESOURCE_URL + "/chat/"
            + this.sessionAccount.userId + "&" +friendId, {
            headers: {'Content-Type': 'application/json'},
            method: 'GET',
        })
        if (response.ok){
            return await response.json();
        }
    }
    async sendMessage(message, friendId){
        await fetch(this.RESOURCE_URL + "/chat/friend/" + friendId, {
            headers: {'Content-Type': 'application/json'},
            method: 'POST',
            body: JSON.stringify({
                "id": this.sessionAccount.userId,
                "message": message,
            })
        })
    }

    async searchUser(keyword){
        let response = await fetch(this.RESOURCE_URL + "/chat/searchUser/" + keyword, {
            headers: {'Content-Type': 'application/json'},
            method: 'GET',
        })
        if (response.ok){
            return response.json();
        }
    }

    async getAllFriends(){
        let response = await fetch(this.RESOURCE_URL + "/chat/friends/getAll/" +
            this.sessionAccount.userId,
            {
                headers: {'Content-Type': 'application/json'},
                method: 'GET',
            })
        if (response.ok){
            return await response.json();
        }
    }

    async addFriend(friendId){
        await fetch(this.RESOURCE_URL + "/chat/addFriend", {
            headers: {'Content-Type': 'application/json'},
            method: 'POST',
            body: JSON.stringify({
                "userId": this.sessionAccount.userId,
                "friendId": friendId,
            })
        })
    }
}