const express = require('express');
const bodyParser = require('body-parser');
const router = express.Router();

//import modules
const multer = require('multer');
const path = require('path');

//import controllers
const adminController = require('../controllers/admin.control');
const phoneController = require('../controllers/phone.control');
const session = require('express-session');
const Passport = require('passport');
const LocalStrategy = require('passport-local').Strategy;
//import models
const Admin = require('../models/admin');
const Phone = require('../models/phone');


//cấu hình Passport
router.use(
    session({
        secret: 'mysecret', //thuôc tính bắt buộc
        resave: true,
        saveUninitialized: true,
        cookie: {
            maxAge: 1000 * 60 * 5,
        },
        //cookie sẽ tồn tại trong 5 phút, nếu xóa dòng code sau thì cookie sẽ hết hạn sau khi đóng trinh duyệt
    })
);

//2 hàm khởi tạo passport
router.use(Passport.initialize());
router.use(Passport.session());
//chứng thực thông tin đăng nhập trên mongoDB
Passport.use(
    new LocalStrategy(
        //email, password là name của thẻ input trong form login, nếu k khai báo mặc định sẽ là username, password
        {
            usernameField: 'email',
            passwordField: 'password',
        },
        (email, password, done) => {
            Admin.findOne({
                email: email,
                password: password
            }, function (err, user) {
                console.log("Đăng nhập thành công: " + email);
                if (err) {
                    console.log(err);
                }
                if (user) {
                    //thành công sẽ trả về true với giá trị user
                    return done(null, user);
                } else {
                    return done(null, false);
                }
            });
        }
    )
);

//sau khi chứng thức thành công passport sẽ gọi hàm .serializeUser() vói tham số user giá trị đã lưu bên trên
//chọn thuộc tính email của user để ghi vào cookie
Passport.serializeUser((user, done) => {
    done(null, user.email);
});
//biến cookieID chính là giá trị user.email bên trên
Passport.deserializeUser((cookieID, done) => {
    Admin.findOne({
        email: cookieID
    }, function (err, user) {
        if (err) {
            console.log(err);
        }
        if (user) {
            return done(null, user);
        } else {
            return done(null, false);
        }
    });
});

//khai báo phương thức xác thực đăng nhập sau
const isAuthenticated = function (request, response, next) {
    if (request.isAuthenticated()) return next();
    response.redirect('/'); //nếu chưa đăng nhập sẽ quay về trang login
};

//cấu hình multer
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, './uploads');
    },
    filename: (req, file, cb) => {
        cb(null, file.originalname);
    },
});

const upload = multer({
    storage: storage,
    //kiểm tra file upload có phải là hình ảnh hay không
    fileFilter: function (req, file, callback) {
        var ext = path.extname(file.originalname);
        if (ext !== '.png' && ext !== '.jpg' && ext !== '.gif' && ext !== '.jpeg') {
            return callback(new Error('Only images are allowed'));
        }
        callback(null, true);
    },
    limits: {
        fileSize: 1024 * 1024 * 5, //giới hạn filesize = 5Mb
    },
});
//phương thức upload file + insert dữ liệu vào mongoDB
router.post('/upload', upload.single('image'), (request, response) => {
    let phone = new Phone({
        manufacturer: request.body.manufacturer,
        name: request.body.name,
        price: request.body.price,
        image: request.file.originalname, //chỉ lấy tên file upload
        category: request.body.category,
        display: request.body.display,
        system: request.body.system,
        camera: request.body.camera,
        cpu: request.body.cpu,
        disk1: request.body.disk1,
        disk2: request.body.disk2,
        sim: request.body.sim,
        pin: request.body.pin,
    });

    phone.save(function (err) {
        if (err) {
            console.log(err);
            return;
        } else {
            response.redirect('/index');
        }
    });
});


//lấy dữ liệu từ form
router.use(bodyParser.urlencoded({
    extended: false
}));
router.use(bodyParser.json());

router.get('/', (req, res) => {
    res.render('Hello');
});

router.get('/login', (req, res) => {
    res.render('SignIn');
});

// router.post('/login', adminController.login);

router.post('/login', Passport.authenticate('local', {
    successRedirect: '/index',
    failureRedirect: '/'
}));

router.get('/register', (req, res) => {
    res.render('SignUp');
});

router.post('/register', adminController.register);

//get All
router.get('/index', isAuthenticated, phoneController.getAll);
//get Watch
router.get('/edit/:id', phoneController.getPhone);
//edit
router.post('/edit', phoneController.edit);
//delete
router.get('/delete/:id', phoneController.delete);

module.exports = router;