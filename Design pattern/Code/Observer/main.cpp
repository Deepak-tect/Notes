#include<bits/stdc++.h>
using namespace std;
class User{
    private: 
        int id;
    public:
        User(int id){
            this->id = id;
        }

        int getId(){
            return this->id;
        }
};

class Handler{
    public :
    virtual void subcribe(User* user) = 0;
    virtual void unSubscribe(User* user) = 0;
    virtual void notify(string message) = 0;   
};

class Group : public Handler{
    private : 
        list<User*> users;
    public:
        void subcribe(User* user){
            users.push_back(user);
        }
        void unSubscribe(User* user){
            users.remove(user);
        }
        void notify(string messages){
            for(auto user : users){
                cout << "Message recieved by " << user->getId() << " : " << messages << endl;
            }
        }
};

int main(){

    User* user1 = new User(1);
    User* user2 = new User(2);
    User* user3 = new User(2);

    Group* group = new Group();
    group->subcribe(user1);
    group->subcribe(user2);
    group->subcribe(user3);
    group->notify("NEW MESSAGE");
    group->unSubscribe(user1);
    group->notify("BYE");

}