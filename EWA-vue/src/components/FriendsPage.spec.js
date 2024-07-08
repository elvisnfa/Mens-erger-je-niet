import {mount} from '@vue/test-utils'
import {MemoryService} from "../adaptors/InMemoryService";
import {FakeUser} from "../models/FakeUser";
import {reactive} from "vue";
import friendsPage from '@/components/friendsPage.vue';

// manages a component instance in the testbed
let wrapper;


beforeEach(async function() {
    let userService = new MemoryService(10000, FakeUser.createSampleUser);

    wrapper = await mount(friendsPage, {
        global: {
            provide: {
                "chatFriend": reactive(userService),
                "SessionService": reactive(userService)
            }
        }
    });
});


it('can find a friend', async function() {
    const searchInput = 'Jacob'; // Input for searching a friend
    const searchBar = wrapper.find('.searchBar input');
    const searchButton = wrapper.find('.searchBar button');

    expect(searchButton.exists(),
        `Cannot find button`)
        .toBe(true);
    expect(searchButton.element.disabled,
        `button has been disabled`)
        .toBeFalsy();

    await searchBar.setValue(searchInput); // Set the search input value
    await searchButton.trigger('click'); // Click the search button


    expect(wrapper.vm.searchInput).toBe('Jacob');

    // Verify that the search functionality has produced the expected results
    const friendItems = wrapper.findAll('.friend-items li');
    friendItems.forEach((friend) => {
        const friendName = friend.find('.friend-name').text();
        expect(friendName).toContain(searchInput);
    });
});


it('creates a proper main page structure', function() {
    //console.log(objectToString(wrapper));
    //console.log(objectToString(wrapper.element));
    expect(wrapper.element.children.length,
        `main page starting with ${wrapper.element.tagName} has no child elements`)
        .toBeGreaterThan(0);
})
