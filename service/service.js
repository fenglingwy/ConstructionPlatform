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
        case '/post/add':
            insertSql(request, response);
            break;
        case '/post/delete':
            deleteSql(request, response);
            break;
        case '/post/query':
            querySql(request, response);
            break;
        case '/post/update':
            updateSql(request, response);
            break;


        case '/apk/latestnumber':
            latestnumber(request, response);
            break;
        case '/apk/downloadapk':
            downloadapk(request, response);
            break;
        case '/uploading':
            upLoading(request, response);
            break;
        case '/downloadlist':
            downloadList(request, response);
            break;
        case '/downloadFile':
            downloadFile(request, response);
            break;
        case '/historyData':
            historyData(request, response);
            break;



        case '/log':
            fs.readFile('./info.log', 'utf-8', (err, data) => {
                if (err) {
                    response.writeHead(404);
                    response.end('file not found.');
                    return false;
                }
                let msg = output.output(data);
                console.log(msg);
                response.writeHead(200, { "Content-Type": "text/html" });
                response.end(msg);
            });
            break;
        default:
            response.writeHead(404);
            response.end('file not found.');
    }
});

//历史数据查询
function historyData(request, response) {
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
        let data = qs.parse(body);
        console.log(data);
        let sql = `SELECT * FROM history_data WHERE timestamp >= "${data.startTime}" AND timestamp <= "${data.endTime}" ORDER BY timestamp asc`;
        // let sql = `SELECT * FROM history_data WHERE timestamp between UNIX_TIMESTAMP('2016-10-09 14:50:49') and UNIX_TIMESTAMP('2016-10-09 14:55:49')`;

        client.query(sql, function (err, result) {
            if (err) {
                response.writeHead(401);
                console.log(err);
                response.end(JSON.stringify({ result: 'error', msg: '失败！', data: '' }));
            } else {

        
                response.writeHead(200);
                response.end(JSON.stringify({ result: 'success', msg: '成功！', data: result }));
            }
        })

    })


}

//文件下载
function downloadFile(request, response) {
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

            let data = qs.parse(body);
            console.log(data);

            //判断文件夹是否存在
            let filePath = path.join('./upLoading', data.fileName);
            let dirpath = path.dirname(filePath);
            if (!fs.existsSync(dirpath)) {
                response.writeHead(404);
                response.end(JSON.stringify({ result: 'error', msg: '失败！', data: null }));
            } else {
                //文件传输
                let readstream = fs.createReadStream(filePath);
                let states = fs.statSync(filePath);
                response.writeHead(200, { 'Content-Disposition': data.fileName, 'Content-Length': states.size });

                readstream.on('data', function (chunk) {
                    response.write(chunk);
                });
                readstream.on('end', function () {
                    response.end();
                });
            }

        }
    })
}

//文件下载列表
function downloadList(request, response) {
    let sql = `select * from upload`;
    client.query(sql, function (err, result) {
        if (err) {
            response.writeHead(401);
            console.log(err);
            response.end(JSON.stringify({ result: 'error', msg: '失败！', data: '' }));
        } else {

            console.log(result);
            response.writeHead(200);
            response.end(JSON.stringify({ result: 'success', msg: '成功！', data: result }));
        }

    })
}


//上传
function upLoading(request, response) {

    console.log(request.headers['content-disposition']);
    console.log(request.headers);

    let fileName = request.headers['content-disposition'];

    let filePath = path.join('./upLoading', "111.mp4");

    //判断文件夹是否存在
    let dirpath = path.dirname(filePath);
    if (!fs.existsSync(dirpath)) {
        fs.mkdirSync(dirpath);
    }

    console.log(filePath);

    let writeStream = fs.createWriteStream(filePath);
    request.pipe(writeStream);

    request.on('end', function () {
        response.writeHead(200);
        response.end(JSON.stringify({ result: 'success', msg: '', data: '上传成功！' }));
    })

    let sql = `insert into upload(file_name,path,timestamp) values("${fileName}","${'./upLoading/' + fileName}",now())`;
    client.query(sql, function (err, result) {
        if (err) {
            response.writeHead(401);
            console.log(err);
            response.end(JSON.stringify({ result: 'error', msg: '添加失败！', data: '' }));
        } else {
            response.writeHead(200);
            response.end(JSON.stringify({ result: 'success', msg: '添加成功！', data: result }));
        }

    })

}

//APK最新版本号
function latestnumber(request, response) {

    fs.readFile('./root/version.txt', function (err, data) {
        if (err) {
            response.writeHead(401);
            response.end(JSON.stringify({ result: 'error', msg: '', data: '' }));
        } else {
            response.writeHead(200);
            response.end(JSON.stringify({ result: 'success', msg: '', data: data + '' }));
        }
    });
}

//下载APK
function downloadapk(request, response) {

    let readstream = fs.createReadStream('./root/app.apk');
    let states = fs.statSync('./root/app.apk');
    response.writeHead(200, { 'Content-Disposition': 'app.apk', 'Content-Length': states.size });

    readstream.on('data', function (chunk) {
        response.write(chunk);
    });
    readstream.on('end', function () {
        response.end();
    });
}


//insert
function insertSql(request, response) {
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
            let data = qs.parse(body);
            console.log(data);

            // let sql = `insert into user(username,password,age,gender) values("${data.username}","${data.password}","${data.age}","${data.gender}")`;
            let key = '(';
            let values = '(';
            for (let k in data) {
                key += k + ',';
                values += '"' + data[k] + '",';
            }
            key = key.substring(0, key.length - 1) + ')';
            values = values.substring(0, values.length - 1) + ')';
            let sql = `insert into user${key} values${values}`;

            client.query(sql, function (err, result) {
                if (err) {
                    response.writeHead(401);
                    console.log(err);
                    response.end(JSON.stringify({ result: 'error', msg: '添加失败！', data: '' }));
                } else {
                    response.writeHead(200);
                    response.end(JSON.stringify({ result: 'success', msg: '添加成功！', data: result }));
                }

            })
        }
    });
}

//delete
function deleteSql(request, response) {
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
            let data = qs.parse(body);
            console.log(data);
            //let sql = `delete from user where username="${data.username}" and password = ${data.password} and age = ${data.age} and gender = "${data.gender}"`;

            let sql = ``;
            for (let k in data) {
                if (!sql) {
                    sql += k + '="' + data[k] + '"';
                } else {
                    sql += ' and ' + k + '="' + data[k] + '"';
                }
            }
            sql = 'delete from user where ' + sql;
            console.log(sql);
            client.query(sql, function (err, result) {
                if (err) {
                    response.writeHead(401);
                    console.log(err);
                    response.end(JSON.stringify({ result: 'error', msg: '删除失败！', data: '' }));
                } else {
                    response.writeHead(200);
                    response.end(JSON.stringify({ result: 'success', msg: '删除成功！', data: result }));
                }

            })
        }
    });
}
//query
function querySql(request, response) {
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
            let data = qs.parse(body);
            console.log(data);

            //let sql = `select ${username} from user`;
            let sql = ``;
            for (let k in data) {
                if (!sql) {
                    sql += k + '="' + data[k] + '"';
                } else {
                    sql += ' and ' + k + '="' + data[k] + '"';
                }
            }
            sql = `select * from user where ${sql}`;
            console.log(sql);

            client.query(sql, function (err, result) {
                if (err != null) {
                    response.writeHead(401);
                    response.end(JSON.stringify({ result: 'error', msg: '查询失败！', data: '' }));
                } else {
                    response.writeHead(200);
                    response.end(JSON.stringify({ result: 'success', msg: '查询成功！', data: result }));
                    console.log(result);
                }
            })
        }
    });
}
//update
function updateSql(request, response) {
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
            let data = qs.parse(body);
            console.log(data);

            let sql = `UPDATE user SET gender='男' WHERE username ='ccc'`;

            client.query(sql, function (err, result) {
                if (err) {
                    response.writeHead(401);
                    console.log(err);
                    response.end(JSON.stringify({ result: 'error', msg: '修改失败！', data: '' }));
                } else {
                    response.writeHead(200);
                    response.end(JSON.stringify({ result: 'success', msg: '修改成功！', data: '' }));
                }

            })
        }
    });
}




server.listen(80, () => {

    client = mysql.createConnection({
        user: 'admin',
        password: '123456',
        host: '192.168.0.210',
        port: 43306,
        database: "building_network"
    });
    client.connect();

    console.log('http service start...')
});