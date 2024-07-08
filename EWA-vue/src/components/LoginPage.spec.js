import { mount } from '@vue/test-utils';
import LoginPage from '@/components/LoginPage.vue';

describe('LoginPage', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = mount(LoginPage, {
            global: {
                // Provide any necessary dependencies or mocks here
                provide: {
                    SessionService: {
                        asyncSignIn: jest.fn(),
                    },
                },
            },
        });
    });

    it('should render the login form', () => {
        // Assert that the login form elements are present
        expect(wrapper.find('.login-form').exists()).toBe(true);
        expect(wrapper.find('form').exists()).toBe(true);
        expect(wrapper.find('v-text-field[label="Username"]').exists()).toBe(true);
        expect(wrapper.find('v-text-field[label="Password"]').exists()).toBe(true);
        expect(wrapper.find('v-btn[type="submit"]').exists()).toBe(true);
        expect(wrapper.find('.register').exists()).toBe(true);
        expect(wrapper.find('router-link[to="/Registerpage"]').exists()).toBe(true);
    });

});
