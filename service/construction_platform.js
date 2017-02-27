let http = require('http');
let url = require('url');
let fs = require('fs');
let qs = require('querystring');
let mysql = require('mysql');
let path = require('path');

let formidable = require('formidable');
let util = require('util');

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
        case '/login':
            login(request, response);
            break;
        case '/register':
            register(request, response);
            break;
        case '/head_img':
            getHeadImg(request, response, res.query.name);
            break;
        case '/image':
            getImage(request, response, res.query.name);
            break;
        case '/reset_psw':
            resetPsw(request, response);
            break;
        case '/version':
            version(request, response);
            break;
        case '/update':
            update(request, response);
            break;
        case '/product_details':
            product_details(request, response);
            break;
        case '/upLoading':
            upLoading(request, response);
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
            html(request, response);


            // response.writeHead(404);
            // response.end('file not found.');
    }
});


//上传
function upLoading(req, res) {

    let form = new formidable.IncomingForm();
    form.encoding = 'utf-8';
    form.uploadDir = "./upLoading/photo";
    form.keepExtensions = true;
    form.maxFieldsSize = 2 * 1024 * 1024;



    form.parse(req, function (err, fields, files) {
        res.writeHead(200, { 'content-type': 'text/plain' });

        for (let e in files) {

            let sql = `insert into photo(mobilePhone,path,type) values('123456','${files[e].path.replace(/\\/g, '\\\\')}','1')`;
            console.log(files[e].path);
            mysqlClient.query(sql, function (err, result) {
                if (err) {
                    res.writeHead(401);
                    console.log(err);
                    res.end(JSON.stringify({ result: 'error', msg: '添加失败！', data: '' }));
                } else {
                    res.writeHead(200);
                    res.end(JSON.stringify({ result: 'success', msg: '添加成功！', data: files[e].path }));
                }

            })
        }


        // upLoading\\upload_b390822e14754d43de3f3f20b4ce2389.txt


        // res.end(util.inspect({ fields: fields, files: files }));
    });


    // console.log(request.headers['content-disposition']);
    // console.log(request.headers);

    // let fileName = request.headers['content-disposition'];

    // let filePath = path.join('./upLoading', "111.jpg");

    // //判断文件夹是否存在
    // let dirpath = path.dirname(filePath);
    // if (!fs.existsSync(dirpath)) {
    //     fs.mkdirSync(dirpath);
    // }

    // console.log(filePath);

    // let writeStream = fs.createWriteStream(filePath);
    // request.pipe(writeStream);

    // request.on('end', function () {
    //     response.writeHead(200);
    //     response.end(JSON.stringify({ result: 'success', msg: '', data: '上传成功！' }));
    // })

    // let sql = `insert into upload(file_name,path,timestamp) values("${fileName}","${'./upLoading/' + fileName}",now())`;
    // client.query(sql, function (err, result) {
    //     if (err) {
    //         response.writeHead(401);
    //         console.log(err);
    //         response.end(JSON.stringify({ result: 'error', msg: '添加失败！', data: '' }));
    //     } else {
    //         response.writeHead(200);
    //         response.end(JSON.stringify({ result: 'success', msg: '添加成功！', data: result }));
    //     }

    // })

}

//登陆
function login(request, response) {
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

        let params = JSON.parse(data.params);
        console.log(data.params);
        console.log(params.username);
        console.log(params.psw);

        let sql = `SELECT username,password,mobilePhone,realityName,address,introduction,phone,email,idcard,role,head_photo FROM user_info WHERE mobilePhone ='${params.username}'`;

        console.log(sql);
        mysqlClient.query(sql, function (err, result) {
            if (err) {
                response.writeHead(401);
                console.log(err);
                response.end(JSON.stringify({ result: 'error', msg: '服务器异常!', data: '' }));
            } else {
                response.writeHead(200);
                if (result.length == 0) {
                    response.end(JSON.stringify({ result: 'error', msg: '该手机未注册!', data: '' }));
                } else if (result[0].password == params.password) {

                    response.end(JSON.stringify({ result: 'success', msg: '登陆成功!', data: JSON.stringify(result[0]) }));
                } else {
                    response.end(JSON.stringify({ result: 'error', msg: '账户或密码输入错误!', data: '' }));
                }
            }
        })

    })

}
//修改密码
function resetPsw(request, response) {
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
        let params = JSON.parse(data.params);
        let sql = `UPDATE user_info SET password = '${params.password}' WHERE mobilePhone ='${params.mobilePhone}'`;

        console.log(sql);
        mysqlClient.query(sql, function (err, result) {
            if (err) {
                response.writeHead(401);
                console.log(err);
                response.end(JSON.stringify({ result: 'error', msg: '服务器异常!', data: '' }));
            } else {
                response.writeHead(200);
                response.end(JSON.stringify({ result: 'success', msg: '密码修改成功!', data: '' }));
            }
        })

    })

}
//注册
function register(request, response) {
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
        let params = JSON.parse(data.params);

        let sql = `SELECT _id FROM user_info WHERE mobilePhone ='${params.mobilePhone}'`;

        console.log(sql);
        mysqlClient.query(sql, function (err, result) {
            if (err) {
                response.writeHead(401);
                console.log(err);
                response.end(JSON.stringify({ result: 'error', msg: '服务器异常!', data: '' }));
            } else {
                response.writeHead(200);
                if (result.length != 0) {
                    response.end(JSON.stringify({ result: 'error', msg: '该手机号已注册!', data: '' }));
                } else {

                    let sql = `insert into user_info(username,password,mobilePhone,realityName,address,introduction,phone,email,idcard,role) 
        values ('${params.username}','${params.password}','${params.mobilePhone}','${params.realityName}','${params.address}','${params.introduction}','${params.phone}','${params.email}','${params.idcard}','${params.role}')`;
                    console.log(sql);
                    mysqlClient.query(sql, function (err, result) {
                        if (err) {
                            response.writeHead(401);
                            console.log(err);
                            response.end(JSON.stringify({ result: 'error', msg: '注册失败!', data: '' }));
                        } else {
                            response.writeHead(200);
                            response.end(JSON.stringify({ result: 'success', msg: '注册成功!', data: "" }));
                        }
                    })
                }
            }
        })
    })

}

//获取图片
function getHeadImg(request, response, imageName) {
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
        let params = data.params;
        //判断文件夹是否存在
        let filePath = path.join('./upLoading/head_photo', imageName);
        let dirpath = path.dirname(filePath);
        if (!fs.existsSync(dirpath)) {
            response.writeHead(404);
            response.end(JSON.stringify({ result: 'error', msg: '文件不存在!', data: null }));
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
    })
}

//获取图片
function getImage(request, response, imageName) {
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
        let params = data.params;
        //判断文件夹是否存在
        let filePath = path.join('./', imageName);

        console.log(filePath);
        let dirpath = path.dirname(filePath);
        if (!fs.existsSync(dirpath)) {
            response.writeHead(404);
            response.end(JSON.stringify({ result: 'error', msg: '文件不存在!', data: null }));
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
    })
}
//version
function version(request, response) {
    request.on('readable', function () {

    });

    request.on('end', function () {

        //判断文件夹是否存在
        let filePath = path.join('./upLoading/apk', "version.txt");
        let dirpath = path.dirname(filePath);
        if (!fs.existsSync(dirpath)) {
            response.writeHead(404);
            response.end(JSON.stringify({ result: 'error', msg: '文件不存在!', data: null }));
        } else {
            //文件传输
            let readstream = fs.createReadStream(filePath);
            let states = fs.statSync(filePath);
            response.writeHead(200, { 'Content-Disposition': 'version.txt', 'Content-Length': states.size });

            readstream.on('data', function (chunk) {
                response.write(chunk);
            });
            readstream.on('end', function () {
                response.end();
            });
        }
    })
}
//version
function product_details(request, response) {

}
//下载apk
function update(request, response) {
    request.on('readable', function () {

    });

    request.on('end', function () {

        //判断文件夹是否存在
        let filePath = path.join('./upLoading/apk', "last.apk");
        let dirpath = path.dirname(filePath);
        if (!fs.existsSync(dirpath)) {
            response.writeHead(404);
            response.end(JSON.stringify({ result: 'error', msg: '文件不存在!', data: null }));
        } else {
            //文件传输
            let readstream = fs.createReadStream(filePath);
            let states = fs.statSync(filePath);
            response.writeHead(200, { 'Content-Disposition': 'version.txt', 'Content-Length': states.size });

            readstream.on('data', function (chunk) {
                response.write(chunk);
            });
            readstream.on('end', function () {
                response.end();
            });
        }
    })
}

function html(req, res) {
    let pathname = __dirname + url.parse(req.url).pathname;
    if (path.extname(pathname) == "") {
        pathname += "/";
    }
    if (pathname.charAt(pathname.length - 1) == "/") {
        pathname += "detail.html";
    }

    fs.exists(pathname, function (exists) {
        if (exists) {
            switch (path.extname(pathname)) {
                case ".html":
                    res.writeHead(200, { "Content-Type": "text/html" });
                    break;
                case ".js":
                    res.writeHead(200, { "Content-Type": "text/javascript" });
                    break;
                case ".css":
                    res.writeHead(200, { "Content-Type": "text/css" });
                    break;
                case ".gif":
                    res.writeHead(200, { "Content-Type": "image/gif" });
                    break;
                case ".jpg":
                    res.writeHead(200, { "Content-Type": "image/jpeg" });
                    break;
                case ".png":
                    res.writeHead(200, { "Content-Type": "image/png" });
                    break;
                default:
                    res.writeHead(200, { "Content-Type": "application/octet-stream" });
            }

            fs.readFile(pathname, function (err, data) {
                res.end(data);
            });
        } else {
            res.writeHead(404, { "Content-Type": "text/html" });
            res.end("<h1>404 Not Found</h1>");
        }
    });
}

server.listen(80, () => {

    mysqlClient = mysql.createConnection({
        user: 'root',
        password: 'root',
        host: 'localhost',
        port: 3306,
        database: "construction_platform"
    });
    mysqlClient.connect();


    console.log('http service start...')
});