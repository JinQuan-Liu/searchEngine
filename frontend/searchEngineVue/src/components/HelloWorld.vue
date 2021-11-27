<template>
  <div class="hello">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal"
             @select="handleSelect">
      <img :src="logoImage">
      <el-menu-item index="1" @click="dialogVisible = true">发布</el-menu-item>
      <div class="test">
        <el-input placeholder="请输入内容" v-model="input" class="input-with-select" @keyup.enter.native="query()">
          <!--          <el-select v-model="select" slot="prepend" placeholder="请选择">-->
          <!--            <el-option label="餐厅名" value="1"></el-option>-->
          <!--            <el-option label="订单号" value="2"></el-option>-->
          <!--            <el-option label="用户电话" value="3"></el-option>-->
          <!--          </el-select>-->
          <el-button slot="append" icon="el-icon-search" @click="query"></el-button>
        </el-input>
      </div>
    </el-menu>
    <el-form v-for="(item,i) in contentList" :key="i" class="textBody">
      <el-form-item class="textItem">
        <div style="font-size: large; font-weight: bold" v-html="item.title"></div>
        <div v-html="item.value"></div>
      </el-form-item>
    </el-form>
    <el-dialog
      title="发布文章"
      :visible.sync="dialogVisible"
      width="50%"
      style="text-align: left"
      :before-close="handleClose"
      close-on-click-modal>
      <label-wrap>标题</label-wrap><br><br>
      <el-input placeholder="请输入标题" type="text" v-model="addTitle" maxlength="100" show-word-limit></el-input><br>
      <br>
      <br>
      <label-wrap style="margin-bottom: 30px">文章内容</label-wrap><br><br>
      <el-input placeholder="请输入内容" type="textarea" v-model="addContent" rows="8" maxlength="2000" show-word-limit></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      input: '',
      select: '',
      activeIndex: '1',
      activeIndex2: '1',
      logoImage: require('../assets/images/logo.png'),
      title: '',
      value: '',
      contentList: [],
      dialogVisible: false,
      addTitle: '',
      addContent: ''
    }
  },
  methods: {
    save() {
      const self = this;
      let input = {title: self.addTitle, content: self.addContent};
      this.$axios.post("/article/save", input).then((response) => {
        self.dialogVisible = false;
        self.queryAll();
      }).catch(function(error){
        console.log(error);
      });
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    query() {
      const self = this;
      if (self.input.toString() === '') {
        self.queryAll();
        return
      }
      self.contentList = [];
      let input = {inputContent: self.input};
      this.$axios.post("/article/fuzzySearch", input).then((response) => {
        console.log(response);
        let data = response.data;
        data.forEach(function(e){
          self.contentList.push({title:e.title,value:e.content})
        });
      }).catch(function(error){
        console.log(error);
      });

    },
    queryAll() {
      const self = this;
      self.contentList = [];
      this.$axios.get("/article/searchAll").then((response) => {
        console.log(response);
        let data = response.data;
        data.forEach(function(e){
          self.contentList.push({title:e.title,value:e.content})
        });
      }).catch(function(error){ARTICLE_INDEX_NAME
        console.log(error);
      });
    }
  },
  created:function() {
    this.queryAll()
  }
}
</script>

<style scoped>
img {
  display: block;
  width: 80px;
  min-width: 96px;
  height: 60px;
  margin-top: calc((48px - 44px)/ 2);
  float: left;
  padding-left: 20px;
  padding-right: 20px;
}
.test {
  float: left;
  width: 550px;
  height: 60px;
  padding-top: 10px;
  padding-left: 200px;
}
.textBody {
  text-align: left;
  padding-left: 50px;
  padding-right: 30px;
  padding-top: 30px;
  width: 70%;
}
.textItem {
  border-bottom: 1px solid #c6e2ff;
}
.listTitle {

}
</style>
