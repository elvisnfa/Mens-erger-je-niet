export class diceAdaptor {
    RESOURCE_URL;

    constructor(resourceUrl) {
        this.RESOURCE_URL = resourceUrl;
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

    async asyncFindAll() {
        return await this.fetchJSon(this.RESOURCE_URL);
    }

    async asyncFindAllInLobby(lobbyId) {
        return await this.fetchJSon(this.RESOURCE_URL + "/" + lobbyId);
    }

    async asyncAllFindOnColorAndID(lobbyId, selectedColor) {
        return await this.fetchJSon(this.RESOURCE_URL + "/" + lobbyId + "/" + selectedColor);
    }

    async asyncFindOnColorAndID(lobbyId, selectedColor) {
        return await this.fetchJSon(this.RESOURCE_URL + "/number/" + lobbyId + "/" + selectedColor);
    }

    async addExtrastep(lobbyId, selectedColor,result) {
        await this.fetchJSon(this.RESOURCE_URL + "/" + lobbyId + "/" + selectedColor + "/" + result, {
            headers: {'Content-Type': 'application/json'},
            method: 'POST',
        },)
    }

    async addStepToRecord(turn) {
        await this.fetchJSon(this.RESOURCE_URL , {
            headers: {'Content-Type': 'application/json'},
            method: 'PUT',
            body: JSON.stringify(turn)
        },)
    }

}