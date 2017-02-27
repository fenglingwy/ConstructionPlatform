

DATA: 0,65,0,0,0,13,24,3,10,0,123,0,98,32,2,26,67,32,131


DATA: 0,65,0,0,0,13,24,3,10, 0,123,  0,98,  0,33,  26,67,  32,131

DATA: 0,65,0,0,0,13,24,3,10, 0,111,  0,98,  0,33,  26,67,  32,131
   
  


                                                 6723     8323
let net = require('net');
let time = require('moment');


let HOST = '192.168.0.60';
let PORT = 502;

let client = new net.Socket();

client.connect(502, '192.168.0.60', function () {

    console.log('CONNECTED TO: ' + HOST + ':' + PORT);

    // 建立连接后立即向服务器发送数据，服务器将收到这些数据 
    // let str = new Buffer('00 41 00 00 00 06 18 03 00 00 00 05', 'base64').toString();
    const buf = new Buffer([0x00, 0x41, 0x00, 0x00, 0x00, 0x06, 0x18, 0x03, 0x00, 0x00, 0x00, 0x05]);




    // console.log('打印：' + str);

    client.write(buf.toString('ascii'), 'ascii');

});

// 为客户端添加“data”事件处理函数  
// data是服务器发回的数据  
client.on('data', function (data) {
    var offset = 9;
    var values = [];
    for (var i = 0; i < 10; i += 2) {
        var high = data.readUInt8(offset + i);
        var low = data.readUInt8(offset + i + 1);
        // console.log(high + ',' + low);
        values.push(high * 256 + low);
    }
    console.log(time().format('YYYY-MM-DD hh:mm:ss') + ':' + values);

    setInterval(function () {
        const buf = new Buffer([0x00, 0x41, 0x00, 0x00, 0x00, 0x06, 0x18, 0x03, 0x00, 0x00, 0x00, 0x05]);
        client.write(buf.toString('ascii'), 'ascii');
    }, 1000)


    // var buf=new Buffer(data,'ascii');
    // ;

    // for (var o of data) {
    //     values.push(o);
    // }

    // console.log('DATA: ' + data.length);
    // console.log('DATA: ' + data.readInt8(0));
    // console.log('DATA: ' + data.readInt8(1));
    // console.log('DATA: ' + data.readInt8(3));
    // console.log('DATA: ' + data.readInt8(4));
    // console.log('DATA: ' + data.readInt8(5));
    // console.log('DATA: ' + data.readInt8(6));
    // console.log('DATA: ' + data.readInt8(7));
    // console.log('DATA: ' + data.readInt8(8));
    // console.log('DATA: ' + data.readInt8(9));
    // 完全关闭连接  
    // client.destroy();

});

// 为客户端添加“close”事件处理函数  
client.on('close', function () {
    console.log('Connection closed');
});  
    // for (var o of data) {
    //     values.push(o);
    // }



    // console.log('DATA: ' + data.length);
    // console.log('DATA: ' + data.readInt8(0));
    // console.log('DATA: ' + data.readInt8(1));
    // console.log('DATA: ' + data.readInt8(3));
    // console.log('DATA: ' + data.readInt8(4));
    // console.log('DATA: ' + data.readInt8(5));
    // console.log('DATA: ' + data.readInt8(6));
    // console.log('DATA: ' + data.readInt8(7));
    // console.log('DATA: ' + data.readInt8(8));
    // console.log('DATA: ' + data.readInt8(9));
    console.log('DATA: ' + values);
    // 完全关闭连接  
    client.destroy();

});

// 为客户端添加“close”事件处理函数  
client.on('close', function () {
    console.log('Connection closed');
});  