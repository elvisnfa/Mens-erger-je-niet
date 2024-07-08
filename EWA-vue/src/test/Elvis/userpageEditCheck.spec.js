import { shallowMount } from '@vue/test-utils';
import UserPage from '../../components/userpage/UserPage.vue';

describe('UserPage', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallowMount(UserPage, {
            global: {
                stubs: ['NavBar'],
                provide: {
                    userService: {
                        asyncGetInfo: jest.fn(() => Promise.resolve({})),
                        asyncDeleteById: jest.fn(),
                        asyncUpdate: jest.fn(),
                    },
                    SessionService: {
                        signOut: jest.fn(),
                    },
                },
            },
        });
    });

    afterEach(() => {
        wrapper.unmount();
    });

    it('renders the component', () => {
        expect(wrapper.exists()).toBe(true);
    });

    it('toggles editing mode when edit button is clicked', async () => {
        const editButton = wrapper.find('.edit-button');

        expect(wrapper.vm.editing).toBe(false);

        await editButton.trigger('click');

        expect(wrapper.vm.editing).toBe(true);

        await editButton.trigger('click');

        expect(wrapper.vm.editing).toBe(false);
    });

    it('calls onDelete and signOut methods when delete button is clicked', async () => {
        const deleteButton = wrapper.find('.delete-button');

        window.confirm = jest.fn(() => true);
        await deleteButton.trigger('click');

        expect(wrapper.vm.userService.asyncDeleteById).toHaveBeenCalled();
        expect(wrapper.vm.SessionService.signOut).toHaveBeenCalled();
    });

    it('does not call onDelete or signOut methods when delete button is clicked and confirm returns false', async () => {
        const deleteButton = wrapper.find('.delete-button');

        window.confirm = jest.fn(() => false);
        await deleteButton.trigger('click');

        expect(wrapper.vm.userService.asyncDeleteById).not.toHaveBeenCalled();
        expect(wrapper.vm.SessionService.signOut).not.toHaveBeenCalled();
    });
});

