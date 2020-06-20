const sql = require('mssql');
const sqlConfig = require('../config/database');


// Database Connection
const pool1 = new sql.ConnectionPool(sqlConfig);
const pool1Connect = pool1.connect();

pool1.on('error', err => {

    console.log(err);
})


// Method to list all Game_EVENTS:

exports.listAllGameEvents = async (req, res) => {

  await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `select GAME_ID, EVENT_ID, PLAYER_ID, GAME_TIME, TEAM_ID, GAME_EVENT_ID FROM GAME_EVENTS_V2`;

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


//Method to create new GAME_EVENT:

exports.createNewGameEvent = async (req, res) => {

    const { GAME_ID, EVENT_ID, PLAYER_ID, GAME_TIME, TEAM_ID, GAME_EVENT_ID } = req.body;

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `insert into GAME_EVENTS_V2 (GAME_ID, EVENT_ID, PLAYER_ID, GAME_TIME, TEAM_ID, GAME_EVENT_ID) values 
                        (`+GAME_ID+`,`+EVENT_ID+`,`+PLAYER_ID+`,`+GAME_TIME+`,`+TEAM_ID+`,`+GAME_EVENT_ID+`)`;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        console.dir(result.recordset);
        res.status(201).send({mensagem: "GAME_EVENT Created"});
    } 
    catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "Error connecting to DB"})
    }
};


// Method to list GAME_EVENT by GAME_ID:

exports.listGameEventByGameID = async (req, res) => {

    const GAME_ID = parseInt(req.params.GAME_ID)

    await pool1Connect;
  
      try {
  
          const request = pool1.request();
          var scriptSQL = `select  GAME_ID, EVENT_ID, PLAYER_ID, GAME_TIME, TEAM_ID, GAME_EVENT_ID FROM GAME_EVENTS_V2 WHERE GAME_ID=` +GAME_ID;

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


  // Method to list GAME_EVENT by GAME_EVENT_ID:

exports.listGameEventByGameEventID = async (req, res) => {

    const GAME_EVENT_ID = parseInt(req.params.GAME_EVENT_ID)

    await pool1Connect;
  
      try {
  
          const request = pool1.request();
          var scriptSQL = `select  GAME_ID, EVENT_ID, PLAYER_ID, GAME_TIME, TEAM_ID, GAME_EVENT_ID FROM GAME_EVENTS_V2 WHERE GAME_EVENT_ID=` +GAME_EVENT_ID;

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


  // Method to update a EVENT:

exports.updateGameEvent = async (req, res) => {

    const GAME_EVENT_ID = parseInt(req.params.GAME_EVENT_ID);

    const { GAME_ID, EVENT_ID, PLAYER_ID, GAME_TIME, TEAM_ID } = req.body;

    await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `update GAME_EVENTS_V2 set GAME_ID=`+GAME_ID+`, EVENT_ID=`+EVENT_ID+`,PLAYER_ID=`+PLAYER_ID+`,GAME_TIME=`+GAME_TIME+`,TEAM_ID=`+TEAM_ID+`
                         where GAME_EVENT_ID=` +GAME_EVENT_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "EVENT Updated"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Database error"})
    }
};


// Method for deleting EVENT:

exports.deleteGameEvent = async (req, res) => {

    const GAME_EVENT_ID = parseInt(req.params.GAME_EVENT_ID);

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `delete from GAME_EVENTS_V2 where GAME_EVENT_ID=` +GAME_EVENT_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "EVENT deleted"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Connection error"})
    }
};

// Method for deleting Game_event By Game:

exports.deleteGameEventsByGameID = async (req, res) => {

    const GAME_ID = parseInt(req.params.GAME_ID);

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `delete from GAME_EVENTS_V2 where GAME_ID=` +GAME_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Games deleted"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Connection error"})
    }
};