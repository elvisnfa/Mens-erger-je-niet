import {mount, shallowMount} from '@vue/test-utils';
import NavBar from '@/components/NavBar.vue';
import { RouterLinkStub } from '@vue/test-utils'; // Stub for router-link

describe('NavBar', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallowMount(NavBar, {
            props: {
                isLoggedIn: false,
            },
        });
    });

    it('displays user sub-menu when user-pic is clicked', async () => {
        // Assert that the sub-menu is initially hidden
        const subMenu = wrapper.vm.$refs.subMenu;
        expect(subMenu.classList.contains('open-menu')).toBe(false);

        // Click on the user-pic
        const userPic = wrapper.find('.user-pic');
        await userPic.trigger('click');

        // Assert that the sub-menu is now visible
        expect(subMenu.classList.contains('open-menu')).toBe(true);
    });
});