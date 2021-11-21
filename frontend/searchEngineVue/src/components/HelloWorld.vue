<template>
  <div class="hello">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal"
             @select="handleSelect">
      <img :src="logoImage">
      <el-menu-item index="1">发布</el-menu-item>
      <div class="test">
        <el-input placeholder="请输入内容" v-model="input" class="input-with-select">
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
        <div>{{item.title}}</div>
        <div>{{item.value}}</div>
      </el-form-item>
    </el-form>
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
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    query() {
      const self = this;
      self.contentList = [];
      let input = {inputContent: self.input};
      // this.$axios.get("/article/fuzzySearch").then((response) => {
      this.$axios.post("/article/fuzzySearch", input).then((response) => {
        console.log(response);
        let data = response.data;
        data.forEach(function(e){
          self.contentList.push({title:e.title,value:e.content})
        });
      }).catch(function(error){
        console.log(error);
      });

    }
  },
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
</style>
