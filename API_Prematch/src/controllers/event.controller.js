const sql = require('mssql');
const sqlConfig = require('../config/database');


// Database Connection
const pool1 = new sql.ConnectionPool(sqlConfig);
const pool1Connect = pool1.connect();

pool1.on('error', err => {

    console.log(err);
})


// Method to list all EVENTS:

exports.listAllEvents = async (req, res) => {

  await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `select EVENT_ID, DESIGNATION FROM EVENTS_V2`;

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


//Method to create new EVENT:

exports.createNewEvent = async (req, res) => {

    const { EVENT_ID, DESIGNATION } = req.body;

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `insert into EVENTS_V2 (EVENT_ID, DESIGNATION) values (`+EVENT_ID+`,'`+DESIGNATION+`')`;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        console.dir(result.recordset);
        res.status(201).send({mensagem: "EVENT Created"});
    } 
    catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "Error connecting to DB"})
    }
};


// Method to list EVENT by EVENT_ID:

exports.listEventByID = async (req, res) => {

    const EVENT_ID = parseInt(req.params.EVENT_ID)

    await pool1Connect;
  
      try {
  
          const request = pool1.request();
          var scriptSQL = `select EVENT_ID, DESIGNATION FROM EVENTS_V2 WHERE EVENT_ID=` +EVENT_ID;

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

exports.updateEvent = async (req, res) => {

    const EVENT_ID = parseInt(req.params.EVENT_ID);

    const { DESIGNATION } = req.body;

    await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `update EVENTS_V2 set DESIGNATION='`+DESIGNATION+`' where EVENT_ID=` +EVENT_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "EVENT Updated"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Database error"})
    }
};


// Method for deleting EVENT:

exports.deleteEvent = async (req, res) => {

    const EVENT_ID = parseInt(req.params.EVENT_ID);

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `delete from EVENTS_V2 where EVENT_ID=` +EVENT_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "EVENT deleted"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Connection error"})
    }
};

// Method for deleting Games By Tournament:

exports.deleteGamesByTournamentID = async (req, res) => {

    const TOURNAMENT_ID = parseInt(req.params.TOURNAMENT_ID);

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `delete from GAMES where TOURNAMENT_ID=` +TOURNAMENT_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Games deleted"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Connection error"})
    }
};