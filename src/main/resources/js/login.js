/*const mysql = require('mysql');

// 创建数据库连接配置
const conn = mysql.createConnection({
  host: '127.0.0.1',
  user: 'r',
  password: '1227178381',
  database: 't'
});

// 建立数据库连接
conn.connect((err) => {
  if (err) {
    console.error('Error connecting to database: ' + err.stack);
    return;
  }
  console.log('Connected to database as id ' + conn.threadId);
});

function enter() {
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  conn.query(`SELECT username, password FROM user WHERE username = '${username}' AND password = '${password}'`, (err, results) => {
    if (err) {
      console.error('Error querying database: ' + err.stack);
      return false;
    }

    if (results.length === 0) {
      console.log('不能登录!');
      connection.end();
      return false; // 阻止表单提交
    } else {
      console.log('登录成功!');
      window.location.href = 'index.html'; // 跳转到 index.html 页面
      connection.end();
      return true; // 允许表单提交
    }
  });

  return false; // 阻止表单提交
}

// 关闭数据库连接

*/
const mysql = require('mysql');

// 创建数据库连接配置
const conn = mysql.createConnection({
  host: '127.0.0.1',
  user: 'r',
  password: '1227178381',
  database: 't'
});

// 建立数据库连接
conn.connect((err) => {
  if (err) {
    console.error('Error connecting to database: ' + err.stack);
    return;
  }
  console.log('Connected to database as id ' + conn.threadId);
});

function log(username, password) {
  return new Promise((resolve, reject) => {
    conn.query(`SELECT username, password FROM user WHERE username = '${username}' AND password = '${password}'`, (err, results) => {
      if (err) {
        console.error('Error querying database: ' + err.stack);
        reject(err);
      }

      if (results.length === 0) {
        console.log('不能登录!');
        conn.end();
        resolve(false);
      } else {
        console.log('登录成功!');
        conn.end();
        resolve(true);
      }
    });
  });
}

// 关闭数据库连接

