const express = require('express');
const adminRoute = require('./routes/routes');
const app = express();
const API = require('./routes/API.routes');
const PORT = process.env.PORT || 3000;

app.use(adminRoute);
app.use(API);
app.use(express.static('uploads'));
app.use(express.static('public'));
app.use('*/css', express.static('public/css'));
app.use('*/js', express.static('public/js'));
app.use('*/img', express.static('public/img'));
app.use('*/audio', express.static('public/audio'));

//Kết nối Mongo DB
const connectDB = require("./models/database");
connectDB();

//cấu hình handlebars
const expressHbs = require('express-handlebars');
app.engine(
    '.hbs',
    expressHbs({
        defaultLayout: '',
    })
);
app.set('view engine', '.hbs');

//Khởi chạy server
app.listen(PORT, () => console.log(`App running on port ${PORT}`));