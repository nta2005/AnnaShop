const Phone = require('../models/phone');

//get tất cả sản phẩm
exports.getAll = function (request, response) {
    Phone.find({})
        .lean()
        .exec(function (error, data) {
            response.render('index', {
                phoneList: data.reverse()
            });
            // console.log(data);
            if (error) {
                log(error);
            }
        });
};

//get 1 sản phẩm
exports.getPhone = function (request, response) {
    Phone.findById(request.params.id)
        .lean()
        .exec((err, doc) => {
            if (!err) {
                response.render('edit', {
                    Phone: doc
                });
            }
        });
};
//chỉnh sửa
exports.edit = function (request, response) {
    Phone.updateOne({
            _id: request.body._id
        }, {
            $set: {
                name: request.body.name,
                price: request.body.price,
                manufacturer: request.body.manufacturer,
                image: request.body.image,
                category: request.body.category,
                display: request.body.display,
                system: request.body.system,
                camera: request.body.camera,
                cpu: request.body.cpu,
                disk1: request.body.disk1,
                disk2: request.body.disk2,
                sim: request.body.sim,
                pin: request.body.pin,
            }
        },
        (err, doc) => {
            if (!err) {
                response.redirect('/index');
            } else {
                console.log('Sửa thất bại!');
            }
        }
    );
};

//xóa sản phẩm
exports.delete = function (request, response) {
    Phone.deleteOne({
        _id: request.params.id
    }, (err, doc) => {
        if (!err) {
            response.redirect('/index');
        } else {
            console.log(err);
        }
    });
};