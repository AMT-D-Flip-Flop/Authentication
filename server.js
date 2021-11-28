/**
 * Date de création     : 15.11.2021
 * Groupe               : AMT-D-Flip-Flop
 * Description          : Authentication server to work locally
 * Remarque             :
 * Source :
 * https://expressjs.com/en/resources/middleware/body-parser.html
 * https://stackoverflow.com/questions/10005939/how-do-i-consume-the-json-post-data-in-an-express-application
 * https://expressjs.com/fr/starter/hello-world.html
 */


const express = require('express');
const app = express();
const port = 3000;
//var  bodyParser = require('body-parser');
var bodyParser = require('body-parser')
class User{
    constructor(username, password){
        this.username = username;
        this.password = password;
    }
}
var users = [];

//app.use(bodyParser.json());
/*

 */
//app.use(express.json());
var jsonParser = bodyParser.json()
app.get('/', (req, res) => {
    res.send('Hello World!')
})

// With middleware
/*app.use('/accounts/register', function(req, res, next){
    users.push(req.body)
    console.log(users)
    res.json({
        "id": 0,
        "username": "test",
        "role": "admin"
    })
    next();
})*/

app.post('/auth/login', jsonParser, function (
    req, res) {
    token = ""

    if(req.body.username === "test" && req.body.password === "test" ){
        token = {
            "token": "string",
            "account": {
                "id": 0,
                "username": "string",
                "role": "string"
            }
        }
        console.log("succs", token)
    }else{
        token = {
            "error": "string"
        }
        console.log("succs", token)
    }
    res.json(token)
})
app.post('/accounts/register', jsonParser, function (
    req, res) {
    console.log(req.body)
    users.push(req.body)

    res.json({
        "username": req.body.username,
        "role": "admin"
    })
})

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`)
})
