const mongoose = require("mongoose");
const MONGO_URI =
  "mongodb+srv://nta:123@cluster0-jdbfo.gcp.mongodb.net/test?retryWrites=true&w=majority";
mongoose.set('useCreateIndex', true);
const connectDB = async () => {
  const conn = await mongoose.connect(MONGO_URI, {
    useNewUrlParser: true,
    useCreateIndex: true,
    useFindAndModify: false,
    useUnifiedTopology: true,
  });
  console.log(`MongoDB Connected: ${conn.connection.host}`);
};
module.exports = connectDB;