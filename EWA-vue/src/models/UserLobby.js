export class UserLobby {
    selected_color;
    user_user_id;
    lobby_id_lobby;
    userId_owner;


    constructor(selected_color, user_user_id, lobby_id_lobby, userId_owner) {
        this.selected_color = selected_color;
        this.user_user_id = user_user_id;
        this.lobby_id_lobby = lobby_id_lobby;
        this.userId_owner = userId_owner;
    }

    static createUserLobby(selected_color, user_user_id, lobby_id_lobby, userId_owner){
        return new UserLobby(selected_color, user_user_id, lobby_id_lobby, userId_owner);
    }

}