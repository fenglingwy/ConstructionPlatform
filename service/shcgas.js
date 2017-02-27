let http = require('http');
let url = require('url');
let fs = require('fs');
let qs = require('querystring');
let mysql = require('mysql');
let path = require('path');

let client = null;

let server = http.createServer((request, response) => {
    //true 解析成对象
    let res = url.parse(request.url, true);
    // console.log(res);
    let d = new Date();
    console.log(`@@${d.toLocaleDateString()} ${d.toLocaleTimeString()}`);
    // console.log(res.query);
    // console.log(res.pathname);

    switch (res.pathname) {
        case '/sms':
            insertSMS(request, response);
            break;

        default:
            response.writeHead(404);
            response.end('file not found.');
    }
});


//insert
function insertSMS(request, response) {
    let body = '';
    request.on('readable', function () {
        let d = request.read();
        if (d) {
            if (typeof d == 'string') {
                body += d;
            } else if (typeof d == 'object' && d instanceof Buffer) {
                body += d.toString('utf8');
            }
        }
    });

    request.on('end', function () {
        if (request.method.toLowerCase() == 'post') {
            console.log(body);
            // let str = qs.parse(body).params;
            // let list = JSON.parse(str);
            // // console.log(list[1]);
            // for (let data of list) {
            //     // let sql = `insert into sms(username,password,age,gender) values("${data.username}","${data.password}","${data.age}","${data.gender}")`;
            //     let key = '(';
            //     let values = '(';
            //     for (let k in data) {
            //         key += k + ',';
            //         values += '"' + data[k] + '",';
            //     }
            //     key = key.substring(0, key.length - 1) + ')';
            //     values = values.substring(0, values.length - 1) + ')';
            let params = qs.parse(body)
             let sms = JSON.parse(params.params);
             console.log(sms);
            let sql = `insert into sms(_id,body) values ('${sms.id}','${sms.body}')`;
            console.log(sql);
            client.query(sql, function (err, result) {
                if (err) {
                    response.writeHead(401);
                    console.log(err);
                    response.end(JSON.stringify({ result: 'error', msg: '添加失败！', data: '' }));
                } else {
                    response.writeHead(200);
                    response.end(JSON.stringify({ result: 'success', msg: '添加成功！', data: `{"id":"${sms.id}"}` }));
                }

            })
        }
        // }
    });

    
}






server.listen(80, () => {

    client = mysql.createConnection({
        user: 'root',
        password: 'root',
        host: '192.168.0.104',
        port: 3306,
        database: "shcgas_sms"
    });
    client.connect();

    console.log('http service start...')
});