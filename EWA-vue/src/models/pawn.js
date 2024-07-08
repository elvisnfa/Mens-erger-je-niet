export class pawn {
    id
    color;
    homePosition;
    onField;
    position;
    previousPosition;
    path;


    constructor(id, color, onField, homePosition, position, previousPosition, path) {
        this.id = id;
        this.color = color;
        this.onField = onField;
        this.homePosition = homePosition;
        this.position = position;
        this.previousPosition = previousPosition;
        this.path = path;
    }

    static createGreenpawn(id,homepos) {
        let pawnColor = ["Green", "yellow", "red", "blue"]
        function generateGreenpath() {
            let greenpath = [];
            for (let i = 0; i <= 50; i++) {
                greenpath.push(i);
            }
            for (let i = 52; i <= 56; i++) {
                greenpath.push(i);
            }
            return greenpath;
        }


        return new this(
            this.id = id,
            this.color = pawnColor[0],
            this.onField = 1,
            this.homePosition = homepos,
            this.position = homepos,
            this.previousPosition = null,
            this.path = generateGreenpath()
        )
    }


    static createYellowpawn(id,homepos) {
        let pawnColor = ["Green", "yellow", "red", "blue"]

        function generateYellowpath() {
            let yellowPath = [];
            for (let i = 13; i <= 51; i++) {
                yellowPath.push(i);
            }
            for (let i = 0; i <= 11; i++) {
                yellowPath.push(i);
            }
            for (let i = 81; i <= 85; i++) {
                yellowPath.push(i);
            }
            return yellowPath;
        }


        return new this(
            this.id = id,
            this.color = pawnColor[1],
            this.onField = 1,
            this.homePosition = homepos,
            this.position = homepos,
            this.previousPosition = null,
            this.path = generateYellowpath()
        )
    }

    static createRedpawn(id,homepos) {
        let pawnColor = ["Green", "yellow", "red", "blue"]

        function generateRedpath() {
            let redPath = [];
            for (let i = 39; i <= 51; i++) {
                redPath.push(i);
            }
            for (let i = 0; i <= 37; i++) {
                redPath.push(i);
            }
            for (let i = 71; i <= 75; i++) {
                redPath.push(i);
            }
            return redPath;
        }

        return new this(
            this.id = id,
            this.color = pawnColor[2],
            this.onField = 1,
            this.homePosition = homepos,
            this.position = homepos,
            this.previousPosition = null,
            this.path = generateRedpath()
        )


    }

    static createbluePawn(id,homepos) {
        let pawnColor = ["Green", "yellow", "red", "blue"]

        function generateBluepath() {
            let bluePath = [];
            for (let i = 26; i <= 51; i++) {
                bluePath.push(i);
            }
            for (let i = 0; i <= 24; i++) {
                bluePath.push(i);
            }
            for (let i = 61; i <= 65; i++) {
                bluePath.push(i);
            }
            return bluePath;
        }

        return new this(
            this.id = id,
            this.color = pawnColor[3],
            this.onField = 1,
            this.homePosition = homepos,
            this.position = homepos,
            this.previousPosition = null,
            this.path = generateBluepath()
        )


    }


}
