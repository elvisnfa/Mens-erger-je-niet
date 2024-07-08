import { mount } from '@vue/test-utils';
import DetailOfflineGame from '../../components/lobby/DetailOfflineGame.vue';

describe('DetailOfflineGame', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = mount(DetailOfflineGame);
    });

    afterEach(() => {
        wrapper.unmount();
    });


    it('displays "Player must select a color" message when start button is clicked without selecting a color', async () => {
        const startButton = wrapper.find('.start');

        await startButton.trigger('click');

        const message = wrapper.find('p');

        expect(message.text()).toContain('Player must select a color');
    });
});