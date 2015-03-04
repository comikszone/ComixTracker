/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function myFunc()
{
    console.log('Hello world');
    document.getElementById("demo").style.color = "red";
};
function handleMessage(data){
//                PF('userDetails').show();
        console.log(data);
        receiveMessage([{name:'msgData',value:data}]);
            };
