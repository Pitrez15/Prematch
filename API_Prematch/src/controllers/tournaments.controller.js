const sql = require('mssql');
const sqlConfig = require('../config/database');


// Database Connection
const pool1 = new sql.ConnectionPool(sqlConfig);
const pool1Connect = pool1.connect();

pool1.on('error', err => {

    console.log(err);
})


// Method to list all Tournaments:

exports.listAllTournaments = async (req, res) => {

  await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `select TOURNAMENT_ID,TOURNAMENT_NAME,FORMAT(START_DATE, 'yyyy/MM/dd') START_DATE,FORMAT(FINISH_DATE, 'yyyy/MM/dd') FINISH_DATE,
                                TOURNAMENT_EMAIL,TOURNAMENT_PHONE,TEAMS_NUMBER,TOURNAMENT_TYPE from TOURNAMENTS`;
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


//Method to create new Tournament:

exports.createNewTournament = async (req, res) => {

    const { TOURNAMENT_ID,TOURNAMENT_NAME,START_DATE,FINISH_DATE,TOURNAMENT_EMAIL,TOURNAMENT_PHONE,TEAMS_NUMBER,TOURNAMENT_TYPE } = req.body;

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `insert into TOURNAMENTS (TOURNAMENT_ID,TOURNAMENT_NAME,START_DATE,FINISH_DATE,TOURNAMENT_EMAIL,TOURNAMENT_PHONE,TEAMS_NUMBER,TOURNAMENT_TYPE) values 
                                                (`+TOURNAMENT_ID+`,'`+TOURNAMENT_NAME+`', convert(date,'`+START_DATE+`'), convert(date,'`+FINISH_DATE+`'),
                                                '`+TOURNAMENT_EMAIL+`',`+TOURNAMENT_PHONE+`,`+TEAMS_NUMBER+`,'`+TOURNAMENT_TYPE+`')`;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        console.dir(result.recordset);
        res.status(201).send({mensagem: "Tournament Created"});
    } 
    catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "Error connecting to DB"})
    }
};


// Method to list Tournaments by ID:

exports.listTournamentByID = async (req, res) => {

    const TOURNAMENT_ID = parseInt(req.params.TOURNAMENT_ID)

    await pool1Connect;
  
      try {
  
          const request = pool1.request();
          var scriptSQL = `select TOURNAMENT_ID,TOURNAMENT_NAME,FORMAT(START_DATE, 'yyyy/MM/dd') START_DATE,FORMAT(FINISH_DATE, 'yyyy/MM/dd') FINISH_DATE,
                            TOURNAMENT_EMAIL,TOURNAMENT_PHONE,TEAMS_NUMBER,TOURNAMENT_TYPE from TOURNAMENTS where TOURNAMENT_ID =` +TOURNAMENT_ID;
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


  // Method to update a tournament:

exports.updateTournament = async (req, res) => {

    const TOURNAMENT_ID = parseInt(req.params.TOURNAMENT_ID);

    const { TOURNAMENT_NAME,START_DATE,FINISH_DATE,TOURNAMENT_EMAIL,TOURNAMENT_PHONE,TEAMS_NUMBER,TOURNAMENT_TYPE } = req.body;

    await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `update TOURNAMENTS set TOURNAMENT_NAME='`+TOURNAMENT_NAME+`',START_DATE=convert(date,'`+START_DATE+`'),FINISH_DATE=convert(date,'`+FINISH_DATE+`'),
                                TOURNAMENT_EMAIL='`+TOURNAMENT_EMAIL+`',TOURNAMENT_PHONE=`+TOURNAMENT_PHONE+`, TEAMS_NUMBER=`+TEAMS_NUMBER+`, TOURNAMENT_TYPE='`+TOURNAMENT_TYPE+`' 
                                where TOURNAMENT_ID=` +TOURNAMENT_ID;
        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Tournament Updated"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Database error"})
    }
};


// Method for deleting tournament:

exports.deleteTournament = async (req, res) => {

    const TOURNAMENT_ID = parseInt(req.params.TOURNAMENT_ID);

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `delete from TOURNAMENTS where TOURNAMENT_ID=` +TOURNAMENT_ID;
        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Tournament deleted"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Connection error"})
    }
};