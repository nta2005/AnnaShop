const Phone = require('../models/phone');

//get All
exports.getAll = async (request, response) => {
    try {
        let phones = await Phone.find({});
        response.send(phones);
    } catch (error) {
        console.log(error);
    }
};

exports.getPhone= async (request, response) => {
    try {
        let phone = await Phone.findById(request.params.id);
        response.send(phone);
    } catch (error) {
        console.log(err);
    }
};

//edit
exports.editPhone = async (request, response) => {
    try {
        let phone = await Phone.findById(request.params.id);
        watch.set(request.body);
        let result = await phone.save();
        response.send(result);
    } catch (error) {
        console.log(err);
    }
};

//xóa
exports.deletePhone = async (request, response) => {
    try {
        let result = await Phone.deleteOne({ _id: request.params.id });
        response.send(result);
    } catch (error) {
        console.log(err);
    }
};