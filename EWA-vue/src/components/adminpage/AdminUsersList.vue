<template>
 <NavBar></NavBar>

    <div>
        <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"/>
        <hr/>
        <div class="container bootstrap snippets bootdey">
            <div class="row">
                <div class="col-lg-10">
                    <div class="main-box no-header clearfix">
                        <div class="main-box-body clearfix">
                            <div class="table-responsive">
                                <table class="table user-list">
                                    <thead>
                                    <tr>
                                        <!-- loop through each value of the fields to get the table header -->
                                        <th v-for="tableField in tableFields" :key='tableField' @click="sortTable(tableField)" >
                                            {{ tableField }} <i class="fa-solid fa-arrow-down-a-z" aria-label='Sort Icon'></i>
                                        </th>
                                        <th>Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <!-- Loop through the list get the each student data -->
                                    <tr v-for="user in users" :key='user'>
                                        <td v-for="tableField in tableFields" :key='tableField' >{{ user[tableField] }}</td>
                                        <td>
                                            <button class="btn btn-primary" @click="showPopUpEdit(user)">Edit</button>
                                            <button class="btn btn-danger" ref="delete-user" id="delete-user" @click="showPopUp(user)">Delete</button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">New User</h5>
                            <button class="btn btn-success" @click="showPopUpAdd()">Add User</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div v-if="showEditUser === true" id="myModal" class="modal">
            <div class="modal-content flex justify-content-center">
                <h2 class="mb-2 text-xl text-green-800 mt-0 font-bold mt-2">{{ "Edit User" }}</h2>
              <v-form>
                <v-text-field label="Username"  type="text" v-model="selectedUser.username" ></v-text-field>
                <v-text-field label="firstname" type="text" v-model="selectedUser.firstname"></v-text-field>
                <v-text-field label="lastname"  type="text" v-model="selectedUser.lastname"></v-text-field>
                <v-text-field label="email"  type="email" v-model="selectedUser.email"></v-text-field>
              </v-form>
                <div class="modal-footer mt-3 flex justify-content-center mt-10">
                    <div>
                        <button class="btn btn-danger" @click="goBack()">
                          Cancel Edit
                        </button>
                        <button class="btn btn-success"  @click="onUpdate()">
                          Update User
                        </button>
                    </div>
                </div>
            </div>
        </div>


      <div v-if="showAddUser === true" id="myModal" class="modal">
        <div class="modal-content flex justify-content-center">
          <h2 class="mb-2 text-xl text-green-800 mt-0 font-bold mt-2">{{ "Add User" }}</h2>
          <v-form>
            <v-text-field label="Username"  type="text" v-model="username" ></v-text-field>
            <v-text-field label="firstname" type="text" v-model="firstname"></v-text-field>
            <v-text-field label="lastname"  type="text" v-model="lastname"></v-text-field>
            <v-text-field label="email"  type="email" v-model="email"></v-text-field>
            <v-text-field label="password" type="password" v-model="password"></v-text-field>
          </v-form>
          <div class="modal-footer mt-3 flex justify-content-center mt-10">
            <div>
              <button class="btn btn-danger"  ref="add-user" id="add-user" @click="goBack()">
                Cancel
              </button>
              <button class="btn btn-success"  @click="createUser()">
                Create User
              </button>
            </div>
          </div>
        </div>
      </div>


        <div v-if="showModal === true" id="myModal" class="modal ">
            <div class="modal-content flex justify-content-center align-items-center">
                <h2 class="mb-2 text-xl text-green-800 mt-0 font-bold mt-2">{{ "VERWIJDEREN" }}</h2>
                <p class="mt-2">Weet je zeker dat je {{ this.selectedUser.username }} wilt gaan verwijderen?<br>
                    Dit account zal voor goed verwijderd worden, ook alle lobbys gekoppeld aan het account worden verwijderd
                </p>
                <div class="modal-footer mt-3 flex justify-content-center mt-10">
                  <div>
                    <button class="btn btn-danger" @click="goBack()">
                      Cancel
                    </button>
                    <button class="btn btn-success"  @click="deleteUser(this.selectedUser.userId)">
                      Delete User
                    </button>
                  </div>
                </div>
            </div>
        </div>

    </div>


    <div class="background">
        <img src="../adminpage/Ludobackground.png">
    </div>
</template>


<script>
import NavBar from "@/components/NavBar.vue";
import {toast} from "vue3-toastify";
import User from "@/models/user";
import user from "@/models/user";


export default {
    name: "AdminUsersList",
    components: {NavBar},
    inject: ['userService', 'loginService'],
    data() {
        return {
            users: [],
            tableFields: null,
            showModal: false,
            showEditUser: false,
            showAddUser: false,
            selectedUser: {},
            username: '',
            email: '',
            firstname: '',
            lastname: '',
            password: null,
        }
    },
    mounted() {
        this.mockArray = this.users;
    },

    watch: {
        searchQuery(query, oldQuery) {
            // if (oldQuery !== null) {
            //
            // }

            this.filteredStudentData = this.users.filter(student => student.Name.includes(query));

            if (oldQuery !== null && oldQuery.length >= query.length || (query.length === 0 && oldQuery.length === 1)) {
                // this.studentData = this.mockArray;
                this.users = this.mockArray.filter(student => student.Name.includes(query));
            } else {
                this.users = this.filteredStudentData
            }
        }
    },

    methods: {

        goBack() {
            this.showModal = false;
            this.showEditUser = false;
            this.showAddUser = false;
        },

        async deleteUser(id) {
            await this.userService.asyncDeleteById(id);
            this.showModal = false
            this.users = await this.userService.asyncFindAll();
            this.users.sort()
        },

        async showPopUp(user) {
            this.selectedUser = user;
            this.showModal = true;
        },
        async showPopUpEdit(user) {
            this.selectedUser = user;
            this.showEditUser = true;

        },

        async showPopUpAdd(){
          this.showAddUser = true;
        },

        sortTable(tableField) {
            if (tableField === "email") {
                for (let i = 0; i < this.users.length - 1; i++) {
                    for (let j = 0; j < this.users.length - i - 1; j++) {
                        if (this.users[j].email > this.users[j + 1].email) {
                            let temp = this.users[j];
                            this.users[j] = this.users[j + 1];
                            this.users[j + 1] = temp;
                        }
                    }
                }
            }
        },
        async onUpdate() {
            // this.selectedUser.firstName = this.firstname;
            // this.selectedUser.lastName = this.firstname;
            // this.selectedUser.username = this.username;
            // this.selectedUser.email = this.email;

            await this.userService.asyncAdminUpdate(this.selectedUser)
            toast.success("Account succesfully edited!");
            this.showEditUser = false
        },

      async createUser() {
        try {
          if (!this.usernameValidated ) {
            const newuser = User.createUser(this.username,this.firstname,this.lastname,this.email,this.password);
            await this.loginService.asyncSave(newuser);
            this.showAddUser = false

          } else {
            toast.error("Error encouterd check your input");
          }
        } catch (e) {
          console.log(e);
          toast.error("User not Created");
        }
      },
    },




    async created() {
        this.users = await this.userService.asyncFindAll();
        this.users.sort()

        this.tableFields = [
            'userId', 'username', 'firstname', 'lastname', "email", "role"
        ]


    },
}


</script>


<style scoped>


.background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1000;
  background-color: rgba(5, 11, 98, 1);
}

.background img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: bottom;
  filter: brightness(0.7);
}

.modal {
    display: block; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0, 0, 0); /* Fallback color */
    background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

.modal-content {
    background-color: #fefefe;
    margin: 13% auto; /* 15% from the top and centered */
    padding: 20px;
    border: 1px solid #888;
    width: 50%; /* Could be more or less, depending on screen size */
}

body{
    background:#eee;
}
.main-box.no-header {
    padding-top: 20px;
}
.main-box {
    background: #FFFFFF;
    -webkit-box-shadow: 1px 1px 2px 0 #CCCCCC;
    -moz-box-shadow: 1px 1px 2px 0 #CCCCCC;
    -o-box-shadow: 1px 1px 2px 0 #CCCCCC;
    -ms-box-shadow: 1px 1px 2px 0 #CCCCCC;
    box-shadow: 1px 1px 2px 0 #CCCCCC;
    margin-bottom: 16px;
    -webikt-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
}
.table a.table-link.danger {
    color: #e74c3c;
}

.label {
    border-radius: 3px;
    font-size: 0.875em;
    font-weight: 600;
}
.user-list tbody td .user-subhead {
    font-size: 0.875em;
    font-style: italic;
}
.user-list tbody td .user-link {
    display: block;
    font-size: 1.25em;
    padding-top: 3px;
    margin-left: 60px;
}
a {
    color: #3498db;
    outline: none!important;
}
.user-list tbody td>img {
    position: relative;
    max-width: 50px;
    float: left;
    margin-right: 15px;
}

.table thead tr th {
    text-transform: uppercase;
    font-size: 0.875em;
}
.table thead tr th {
    border-bottom: 2px solid #e7ebee;
}
.table tbody tr td:first-child {
    font-size: 1.125em;
    font-weight: 300;
}
.table tbody tr td {
    font-size: 0.875em;
    vertical-align: middle;
    border-top: 1px solid #e7ebee;
    padding: 12px 8px;
}
a:hover{
    text-decoration:none;
}

.btn {
    margin-right: 5px;
    }

</style>