#include<bits/stdc++.h>
using namespace std;

class SGTree{

    vector<int>seg;

    public:
    SGTree(int n){
        seg.resize(n);
    }

    void build(int index , int low , int high , vector<int>arr){
        if(low == high){
            seg[index] = arr[low];
            return;
        }
        int mid = (low + high)/2;
        build(2*index + 1 , low , mid , arr);
        build(2*index + 2 , mid +1 , high , arr);
        seg[index] = min(seg[2*index + 1] , seg[2*index + 2]);
    }

    int query(int index , int l , int r , int low , int high){
        // no overlap
        if(l > high || r < low) return INT_MAX;

        // complete overlap
        if(l <= low && r >= high) return seg[index];

        // partial overlap 
        int mid = (low + high)/2;
        int left = query(2*index +1 , l , r , low , mid);
        int right = query(2*index +2 , l , r , mid+1, high);
        return min(left , right);
    }

    void update(int index , int low , int high , int i , int val){
        if(low == high){
            seg[index] = val;
            return;
        }
        int mid = (low + high)/2;
        if(i <= mid) update(2*index +1 , low , mid , i , val);
        else update(2*index +2 , mid+1 , high , i , val);
        seg[index] = min(seg[2*index + 1] , seg[2*index + 2]);
    }

};

int main(){
    int n = 0;
    int q = 0;
    cin >> n;
    SGTree seg(4*n);
    vector<int>arr(n);
    for(int i = 0 ; i < n ; i++){
        cin >> arr[i];
    }
    seg.build(0 , 0 , n-1 , arr);
    cin >> q;
    while(q--){
        int type;
        cin >> type;
        if(type == 1){
            int l , r;
            cin >> l >> r;
            int ans = seg.query(0 , l , r, 0 , n-1);
            cout << ans << endl;
        }
        else{
            int i , val;
            cin >> i >> val;
            seg.update(0 , 0 , n-1 , i , val);
            arr[i]= val;
        }

    }

}
