#include <iostream>
#include <vector>
#include <fstream>
#include <time.h>
#include <math.h>

using namespace std;
//
//vector<bool> erosthenes(vector<bool> &v, long index, long total){
//
//            for(long i = 2; i<total; i++){
//                if(index*i>=total-1) break;
//                if(!v.at(index*i)){
//                    continue;
//                }
//                else
//                {
//                    v.at(index*i) = false;
//                }
//            }
//            index++;
//            if(index>=total-1) return v;
//            while(!v.at(index)){
//                index++;
//                if(index>=total-1) return v;
//            }
//            cout<<index<<"\n";
//            return erosthenes(v, index, total);
//    }
int main() {
            vector<bool> v;
            for(long i = 0; i<1000000000; i++)
            v.push_back(true);

            time_t seconds;
            seconds = time(NULL);
            cout <<"Start erosthenes"<< "\n";
            //v = erosthenes(v, 2, v.size());
            bool run = true;
            long index = 2;
            long total = v.size();

        while(run){
            for(long i = 2; i<total; i++){
                if(index*i>=total-1) break;
                if(!v.at(index*i)){
                    continue;
                }
                else
                {
                    v.at(index*i) = false;
                }
            }
            index++;

            if(index>=total-1) run = false;
            while(!v.at(index)){
                index++;
                if(index>=total-1) run = false;
            }
            cout<<index<<"\n";
    }
            ofstream fil("tall.txt");
            if(fil.is_open())
            for(long i = 2; i<v.size()-1; i++)
            {
            if(v.at(i)){
            fil<<i<<"\n";
            cout<<i<<"\n";
            }
            }
            fil.close();
            cout <<"Ferdig erothenes"<< time(NULL)-seconds<<"\n";
            return 0;
}
