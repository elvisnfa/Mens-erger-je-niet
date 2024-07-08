export class playermove {
    tokenId;
    tokenPos;
    onField;

    constructor(tokenId, tokenPos, onField) {
        this.tokenId = tokenId;
        this.tokenPos = tokenPos;
        this.onField = onField;
    }

    static createPlayermove (tokenId,tokenPos,onField){
        return new playermove(tokenId,tokenPos,onField);
    }

    static createSample() {
        const playerm = new playermove();
        playerm.tokenId = 101;
        playerm.tokenPos = 51;
        playerm.onField = 2;
        return playerm;
    }
}
