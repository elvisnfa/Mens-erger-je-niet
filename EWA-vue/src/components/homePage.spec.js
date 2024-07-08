import { mount } from '@vue/test-utils';
import HomePage from '@/components/HomePage.vue';

describe('HomePage', () => {
    it('renders the homepage content correctly', () => {
        const wrapper = mount(HomePage);

        // Check if the homepage content is rendered correctly
        expect(wrapper.find('h1').text()).toBe('Welcome to Hva Ludo!');
        expect(wrapper.find('p').text()).toBe('Our website offers a virtual version of Ludo that you can play' +
            ' online with friends or against other players from around the world.' +
            ' You can customize the game settings, such as the number of players and the difficulty level,' +
            ' to make the game more challenging and exciting Register to enjoy all functionalities! Also when' +
            ' registered, don\'t forget to log in!');
        expect(wrapper.find('router-link[to="/Loginpage"]').text()).toBe('Login');
        expect(wrapper.find('router-link[to="/Registerpage"]').text()).toBe('Register here');
    });

    it('displays the ludo board image', () => {
        const wrapper = mount(HomePage);

        // Check if the ludo board image is displayed
        expect(wrapper.find('.ludo-board').exists()).toBe(true);
        expect(wrapper.find('.ludo-board').attributes('src')).toBe('C:\\Users\\Ilias Aabi\\iss201_2\\EWA-vue\\src\\assets\\ludo-board.jpg');
    });
});


