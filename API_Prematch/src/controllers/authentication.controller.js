const jwt = require('jsonwebtoken');
const sql = require('mssql');
const sqlConfig = require('../config/database');
const bcrypt = require('bcrypt');
const { json } = require('express');
const { password } = require('../config/database');
require('dotenv').config();


// Criar ligação à base de dados para ser usada pelos métodos
const pool1 = new sql.ConnectionPool(sqlConfig);
const pool1Connect = pool1.connect();

pool1.on('error', err => {

    console.log(err);
})


//Registry
exports.registry = async (req, res, next) => {

    const { FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD } = req.body;
    PASSWORDHS = await bcrypt.hash(PASSWORD, 5);
    await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `insert into USERS (FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD) values 
                        ('` + FIRST_NAME + `','` + LAST_NAME +`','` + USERNAME + `','` + EMAIL +`','` + PASSWORDHS + `')`;
                        
        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "SUCCESSFULL USER REGISTRY!"});
    } 

    catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "ERROR CONNECTING DB"})
    }
};


//Login
exports.login = async (req, res, next) => {

    const { USERNAME, PASSWORD } = req.body;
    await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `select USERNAME, PASSWORD from USERS where USERNAME = '` + USERNAME + `'`;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        if(result.recordset.length==0){

            res.status(400).send({mensagem: "USER NOT FOUND IN DB!"})
            return;
        }
        const utilBD = result.recordset[0].USERNAME;
        const passBD = result.recordset[0].PASSWORD;

        if (!await bcrypt.compare(PASSWORD, passBD)){

            res.status(400).send({mensagem: "WRONG PASSWORD!!"})
            return;
        }

        var token = jwt.sign({ utilBD }, process.env.API_SECRET, {

            expiresIn: 3600 // expires in 60min
        });

        res.status(200).send({ auth: true, token: token });
        return;
    } 
      
    catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "ERROR CONNECTING TO DB"})
    }
};


//List User

exports.listUserByUsername = async (req, res) => {

    const USERNAME = req.params.USERNAME

    await pool1Connect;
  
    try {
  
        const request = pool1.request();
        var scriptSQL = `select FIRST_NAME,LAST_NAME,USERNAME,EMAIL,PASSWORD from USERS where USERNAME = '` + USERNAME + `'`;
        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        console.dir(result.recordset);
        res.status(201).json(result.recordset);
    } 
    catch (err) {
  
        console.error('SQL error', err);
         res.status(500).send({mensagem: "Error connecting to DB"})
    }
};


// Recover Password

exports.recoverPassword = async (req, res) => {

    const USERNAME = req.params.USERNAME;

    const { PASSWORD } = req.body;
    PASSWORDHS = await bcrypt.hash(PASSWORD, 5);

    await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `update USERS set PASSWORD= '`+PASSWORDHS+`' where USERNAME= '` + USERNAME + `'`;
        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Password Recovered"});
    } catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "Database error"})
    }
};


// update User

exports.updateUser = async (req, res) => {

    const USERNAME = req.params.USERNAME;

    const { FIRST_NAME,LAST_NAME,EMAIL } = req.body;

    await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `update USERS set FIRST_NAME= '`+FIRST_NAME+`',LAST_NAME= '`+LAST_NAME+`',EMAIL= '`+EMAIL+`'
                        where USERNAME= '` + USERNAME + `'`;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "User Updated"});
    } catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "Database error"})
    }
};