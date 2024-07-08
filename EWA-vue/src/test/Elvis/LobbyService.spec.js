import {InMemoryService} from "@/adaptors/InMemoryService";
import {Lobby} from "@/models/Lobby";


describe('LobbyServiceTest', () => {
    let service;


    beforeEach(function () {
        service = new InMemoryService(0, Lobby.createSample)
    });


    it('findAll returns all', function () {
        let lobby = service.findAll();
        expect(lobby,
            'lobbys.findAll does not return all lobbys')
            .toStrictEqual(service.entities);
    })


    it('findById returns the specified lobbys', function () {
        const lobby = service.entities[0];

        expect(service.findById(1),
            'Lobby.findById cannot find Lobby-${lobby.id})'
    ).toStrictEqual(lobby)

    })
});