// let HOST = '192.168.0.60';
// let PORT = 502;
let net = require('net');
let time = require('moment');
let mysql = require('mysql');

let client = new net.Socket();


let connection = mysql.createConnection({
    host: '10.0.0.241',
    port: '3306',
    user: 'admin',
    password: '123456',
    database: 'mariadb'
});

connection.connect();

// connection.end();//关闭数据库



client.connect(502, '192.168.0.60', function () {
    // 建立连接后立即向服务器发送数据，服务器将收到这些数据 
    // let str = new Buffer('00 41 00 00 00 06 18 03 00 00 00 05', 'base64').toString();
    const buf = new Buffer([0x00, 0x41, 0x00, 0x00, 0x00, 0x06, 0x18, 0x03, 0x00, 0x00, 0x00, 0x05]);
    client.write(buf.toString('ascii'), 'ascii');
});




// 为客户端添加“data”事件处理函数  
// data是服务器发回的数据  
client.on('data', function (data) {
    console.log(data);
    try {
        let address = data.readUInt8(0) * 256 + data.readUInt8(1);
        console.log(address);

        let offset = 9;
        let values = [];
        let sn = 1;

        for (let i = 0; i < 10; i += 2) {
            let high = data.readUInt8(offset + i);
            let low = data.readUInt8(offset + i + 1);
            // values.push(high * 256 + low);
            // values.push({ time: time, address: address, sn: sn, value: high * 256 + low })
            connection.query('INSERT INTO maria(_id,time,sn,value,address) VALUES(0,?,?,?,?)',
                [time().format('YYYY-MM-DD HH:mm:ss'), sn, high * 256 + low, address],
                function (err, result) {
                    console.log('数据库插入id：' + result.insertId);
                });
            sn = sn + 1;
        }



        setTimeout(function () {
            const buf = new Buffer([0x00, 0x41, 0x00, 0x00, 0x00, 0x06, 0x18, 0x03, 0x00, 0x00, 0x00, 0x05]);
            // const buf = new Buffer([0x00, 0x33, 0x00, 0x00, 0x00, 0x11, 0x18, 0x10, 0x00, 0x00, 0x00, 0x05, 0x0A, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x05]);
            client.write(buf.toString('ascii'), 'ascii');
        }, 1000)

        // console.log(time().format('YYYY-MM-DD hh:mm:ss') + ':' + values);
        // insertDB(time().format('YYYY-MM-DD hh:mm:ss') + '', values + '', address);

    } catch (error) {
        const buf = new Buffer([0x00, 0x41, 0x00, 0x00, 0x00, 0x06, 0x18, 0x03, 0x00, 0x00, 0x00, 0x05]);
        client.write(buf.toString('ascii'), 'ascii');
        console.log('错误信息：' + error)
    }

    // 完全关闭连接  
    // client.destroy();

});

// 为客户端添加“close”事件处理函数  
client.on('close', function () {
    console.log('Connection closed');
});


client.on('error', function () {
    console.log(error + '..');
}
);









