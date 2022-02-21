// Vue3 관련 설정 파일
const path = require('path')

module.exports = {
  devServer: {
    https: false,
    port: 8080,
    open: false,
    historyApiFallback: true,
    hot: true,
    // host header check
    disableHostCheck: true
  },
  lintOnSave: false,
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.join(__dirname, 'src/')
      }
    }
  }
};