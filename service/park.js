let net = require('net');
 
let server = net.createServer((socket) => {
 
    socket.on('data', function(data) {
		
		console.log(data);
    });
 
    socket.on('end', () => {
        console.log('���ӶϿ�');
    });
 
 
});
 
server.listen(6000, () => {
	
	
	
    console.log('server bound')
});
 
 

 