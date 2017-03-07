let http = require('http');
let url = require('url');
let fs = require('fs');
let qs = require('querystring');
let mysql = require('mysql');
let path = require('path');
let time = require('moment');

let formidable = require('formidable');
let util = require('util');

let client = null;

let server = http.createServer((request, response) => {
    //true query解析成对象
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
        case '/getData':
            getData(request, response);
            break;
        case '/getSoldData':
            getSoldData(request, response);
            break;
        case '/addProduct':
            addProduct(request, response);
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

//add产品
function addProduct(request, response) {
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

        let sql = `insert into product(name,price,timestamp,imgUrl,product_type,sales_type,user_id) 
        values ('${params.name}','${params.price}','${time().format('YYYY-MM-DD HH:mm:ss')}','${params.imgUrl.replace(/\\/g, '\\\\')}','${params.product_type}','${params.sales_type}','${params.user_id}')`;
        console.log(sql);
        mysqlClient.query(sql, function (err, result) {
            if (err) {
                response.writeHead(401);
                console.log(err);
                response.end(JSON.stringify({ result: 'error', msg: '添加失败!', data: '' }));
            } else {
                response.writeHead(200);
                response.end(JSON.stringify({ result: 'success', msg: '添加成功!', data: "" }));
            }
        })



    })

}

//获取产品数据
function getData(request, response) {
    let res = url.parse(request.url, true);
    let product_type = res.query.product_type;
    let sales_type = res.query.sales_type;
    let keyword = res.query.keyword;

    let sql = ``;
    if (product_type == undefined && keyword == undefined && sales_type == undefined) {
        sql = `SELECT * FROM product order by timestamp desc`;
    }
    else if (product_type != undefined && keyword == undefined && sales_type == undefined) {
        sql = `SELECT * FROM product where product_type = ${product_type}  order by timestamp desc`;
    }
    else if (product_type == undefined && keyword != undefined && sales_type == undefined) {
        sql = `SELECT * FROM product where  name like '%${keyword}%' order by timestamp desc`;
    }
    else if (product_type == undefined && keyword == undefined && sales_type != undefined) {
        sql = `SELECT * FROM product where sales_type = ${sales_type} or sales_type = 3 order by timestamp desc`;
    }
    else if (product_type != undefined && keyword != undefined && sales_type == undefined) {
        sql = `SELECT * FROM product where product_type = ${product_type}  and name like '%${keyword}%' order by timestamp desc`;
    }
    else if (product_type != undefined && keyword == undefined && sales_type != undefined) {
        sql = `SELECT * FROM product where product_type = ${product_type} and (sales_type = ${sales_type} or sales_type = 3) order by timestamp desc`;
    }
    else if (product_type == undefined && keyword != undefined && sales_type != undefined) {
        sql = `SELECT * FROM product where (sales_type = ${sales_type} or sales_type = 3) and name like '%${keyword}%' order by timestamp desc`;
    }
    else if (product_type != undefined && keyword != undefined && sales_type != undefined) {
        sql = `SELECT * FROM product where product_type = ${product_type} and (sales_type = ${sales_type} or sales_type = 3)and name like '%${keyword}%' order by timestamp desc`;
    }




    console.log(sql);
    mysqlClient.query(sql, function (err, result) {
        if (err) {
            response.writeHead(401);
            console.log(err);
            response.end(JSON.stringify({ result: 'error', msg: '服务器异常!', data: '' }));
        } else {
            response.end(JSON.stringify({ result: 'success', msg: '', data: JSON.stringify(result) }));
        }
    })
}
//获取产品数据
function getSoldData(request, response) {
    let res = url.parse(request.url, true);
    let type = res.query.sales_type;

    let sql = `SELECT * FROM sold_product where sales_type = ${type} AND user_id = 1 order by timestamp desc`;
    console.log(sql);

    mysqlClient.query(sql, function (err, result) {
        if (err) {
            response.writeHead(401);
            console.log(err);
            response.end(JSON.stringify({ result: 'error', msg: '服务器异常!', data: '' }));
        } else {
            response.end(JSON.stringify({ result: 'success', msg: '', data: JSON.stringify(result) }));
        }
    })
}


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

    });

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

        let sql = `SELECT * FROM user_info WHERE mobilePhone ='${params.username}'`;

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

    //判断文件夹是否存在
    let filePath = path.join('./', imageName);

    console.log(filePath);
    fs.exists(filePath, function (exists) {
        if (exists) {
            fs.readFile(filePath, function (err, data) {
                response.end(data);
            });
        } else {
            response.writeHead(404);
            response.end(JSON.stringify({ result: 'error', msg: '文件不存在!', data: null }));
        }

        // let dirpath = path.dirname(filePath);
        // if (!fs.existsSync(dirpath)) {
        //     response.writeHead(404);
        //     response.end(JSON.stringify({ result: 'error', msg: '文件不存在!', data: null }));
        // } else {
        //     //文件传输
        //     let readstream = fs.createReadStream(filePath);
        //     let states = fs.statSync(filePath);
        //     response.writeHead(200, { 'Content-Disposition': data.fileName, 'Content-Length': states.size });

        //     readstream.on('data', function (chunk) {
        //         response.write(chunk);
        //     });
        //     readstream.on('end', function () {
        //         response.end();
    });
    // }

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