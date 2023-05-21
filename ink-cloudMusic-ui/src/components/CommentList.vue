<template>
  <div class="comments">
    <div class="comment_hd">
      <h2>
        评论<em>共{{ total }}条评论</em>
      </h2>
    </div>
    <el-form class="comment_box">
      <div class="comment_textarea">
        <div class="pre">{{ msg }}</div>
        <textarea
          class="J_inpBox inp-paragraph"
          name="leave_msg"
          id="leave_msg"
          placeholder="期待你的神评论……"
          v-model="msg"></textarea>
      </div>
      <div class="comment_box_footer">
        <el-button type="primary" color="#ff641e" @click="subComment"
          >评论</el-button
        >
        <span class="comment_limit"
          ><em class="num">{{ msg.length }}</em
          >/{{ maxLen }}</span
        >
      </div>
    </el-form>

    <div class="comment_area">
      <template v-if="comments.length">
        <div
          class="comment_item"
          v-for="(item, index) in comments"
          :key="item.commentId + index + ''">
          <!-- 用户头像 -->
          <router-link
            :to="{ path: '/user', query: { id: item.user.userId } }"
            class="comment_avatar">
            <el-image :src="item.user.avatarUrl + '?param=120y120'">
              <div slot="placeholder" class="image-slot">
                <i class="iconfont icon-placeholder"></i>
              </div>
            </el-image>
          </router-link>
          <!-- 评论内容 -->
          <div class="comment_info">
            <!-- 用户昵称 -->
            <router-link
              :to="{ path: '/user', query: { id: item.user.userId } }"
              >{{ item.user.nickname }}</router-link
            >
            <!-- 评论内容 -->
            <div class="comment_content">
              {{ item.content }}
            </div>
            <!-- 评论对象 -->
            <div
              class="comment_reply"
              v-for="replyItem in item.beReplied"
              :key="replyItem.beRepliedCommentId">
              <router-link
                :to="{ path: '/user', query: { id: replyItem.user.userId } }"
                >{{ replyItem.user.nickname }}</router-link
              >: {{ replyItem.content }}
            </div>
            <!-- 评论时间 及 操作 -->
            <div class="comment_footer">
              <div class="comment_time">
                {{ $utils.formatMsgTime(item.time) }}
              </div>
              <div class="comment_oper">
                <em
                  class="comment_del"
                  v-if="userInfo && userInfo.userId === item.user.userId"
                  @click="delComment(item)"
                  ><i class="iconfont icon-del"></i
                ></em>
              </div>
            </div>
          </div>
        </div>
        <!-- 分页插件 -->
        <div class="pagination">
          <el-pagination
            :hide-on-single-page="true"
            @current-change="currentChange"
            layout="prev, pager, next"
            :page-count="total">
          </el-pagination>
        </div>
      </template>
      <!-- 若无评论时 -->
      <div v-else class="comments_status">
        <div class="loading" v-if="!isEmpty">评论加载中...</div>
        <div class="empty" v-else>
          <p><i class="iconfont icon-empty-main"></i></p>
          <p>暂无留言</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import { ElMessageBox } from "element-plus";
  import {
    computed,
    getCurrentInstance,
    onMounted,
    Ref,
    ref,
    watch,
  } from "vue";
  import { useStore } from "vuex";

  const props = defineProps({
    sId: {
      type: [String, Number],
      default: 0,
      required: true,
    },
    type: {
      type: Number,
      default: 0,
      required: true,
    },
  });

  const { proxy } = getCurrentInstance();
  const store = useStore();
  const msg: Ref<string> = ref("");
  const maxLen: Ref<number> = ref(140);
  const curId = ref(props.sId);
  const limit: Ref<number> = ref(20);
  const offset: Ref<number> = ref(0);
  const before: Ref<number> = ref(0);
  const hotComments: Ref<any[]> = ref([]);
  const comments: Ref<any[]> = ref([]);
  const total: Ref<number> = ref(0);
  const currentPage: Ref<number> = ref(0);
  const isEmpty: Ref<boolean> = ref(false);
  const replyCommentId: Ref<number> = ref(0);
  const replyIndex: Ref<number> = ref(-1);

  const userInfo = computed(() => store.getters.userInfo);
  const isLogin = computed(() => store.getters.isLogin);

  // 获取页面评论类型
  const getComment = () => {
    // 0: 歌曲 1: mv 2: 歌单 3: 专辑  4: 电台 5: 视频 6: 动态
    switch (props.type) {
      case 0:
        getCommentList("commentSong");
        break;
      case 1:
        getCommentList("commentMv");
        break;
      case 3:
        getCommentList("albumComment");
        break;
      default:
        getCommentList("commentVideo");
        break;
    }
  };

  // 获取评论信息
  const getCommentList = async (type) => {
    const { data: res } = await proxy.$http[type]({
      id: curId.value,
      limit: limit.value,
      offset: offset.value,
      before: before.value,
      timestamp: new Date().valueOf(),
    });

    msgHandler(res);
  };

  // 评论消息Handler
  const msgHandler = (res) => {
    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    total.value = res.total;
    hotComments.value = res.hotComments || [];
    hotComments.value.map((item) => {
      item.isHot = true;
      return item;
    });
    comments.value = [...hotComments.value, ...res.comments];
    // 当前评论是否为空
    isEmpty.value = before.value === 0 && !comments.value.length;
  };

  // 发布/删除/回复评论
  const commentHandler = async (t, content, commentId?) => {
    const params = {
      // 0删除 1发送 2回复
      t: t,
      // 0: 歌曲 1: mv 2: 歌单 3: 专辑  4: 电台 5: 视频 6: 动态
      type: props.type,
      // 对应资源id
      id: curId.value,
      // 发送的内容/对应内容的id
      content: content,
      // 回复的评论id
      commentId: commentId,
    };
    const { data: res } = await proxy.$http.comment(params);
    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }

    msg.value = "";
    getComment();
    if (t === 0) {
      console.log("删除评论成功！");
    } else if (t === 1) {
      console.log("评论成功！");
    } else if (t === 2) {
      console.log("回复评论成功！");
      replyCommentId.value = 0;
      replyIndex.value = -1;
    }
  };

  // 发布评论
  const subComment = async () => {
    if (!isLogin.value) {
      store.commit("setLoginDialog", true);
      return;
    }

    let a = {
      content: msg.value,
      user: {
        userId: userInfo.value.userId,
        nickname: userInfo.value.userNickname,
        avatarUrl: userInfo.value.userImgUrl,
      },
      time: parseInt(new Date().getTime() + ""),
    };
    comments.value = [a, ...comments.value];
  };

  // 删除评论
  const delComment = (item) => {
    ElMessageBox.confirm("确定删除评论？", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      center: true,
    })
      .then(() => {
        comments.value.shift();
      })
      .catch(() => {});
  };

  // 回复评论
  const replyComment = (item, index) => {
    if (!isLogin.value) {
      store.commit("setLoginDialog", true);
      return;
    }
    replyCommentId.value =
      replyCommentId.value === item.commentId && replyIndex.value === index
        ? 0
        : item.commentId;
    replyIndex.value = index;
  };

  // 验证消息
  const replyMsg = (msg) => {
    if (!isLogin.value) {
      store.commit("setLoginDialog", true);
      return;
    }
    commentHandler(2, msg, replyCommentId.value);
  };

  // 给评论点赞
  const likeComment = async (item) => {
    if (!isLogin.value) {
      store.commit("setLoginDialog", true);
      return;
    }
  };

  // 分页
  const currentChange = (page) => {
    offset.value = (page - 1) * limit.value;
    getComment();
  };

  onMounted(() => {
    getComment();
  });

  watch(
    () => props.sId,
    (newVal, oldVal) => {
      curId.value = newVal;
      offset.value = 0;
      getComment();
    }
  );

  watch(
    () => msg.value,
    () => {
      msg.value =
        maxLen.value >= parseInt(msg.value)
          ? msg.value
          : msg.value.substring(0, maxLen.value);
    }
  );
</script>

<style scoped lang="less">
  .comments {
    padding-top: 30px;
  }

  .comment_hd {
    display: flex;
    align-items: center;
    height: 60px;
    font-size: 0;

    h2 {
      flex: 1;
      font-size: 24px;
      line-height: 24px;
      font-weight: normal;

      em {
        padding-left: 20px;
        font-size: 14px;
        line-height: 14px;
        font-style: normal;
        color: #999;
      }
    }

    .userInfo {
      .avatar {
        width: 30px;
        height: 30px;
        border-radius: 4px;
        vertical-align: top;
      }

      span {
        display: inline-block;
        padding: 0 20px;
        line-height: 30px;
        font-size: 14px;
        color: #666;
      }
    }
  }

  .comment_box {
    .comment_textarea {
      position: relative;
      border: 1px solid #e9e9e9;
      border-radius: 4px;
      background: #fff;
    }

    .pre {
      visibility: hidden;
    }

    .inp-paragraph {
      position: absolute;
      top: 0;
      left: 0;
      height: calc(100% - 20px);
      resize: none;
      width: auto;
      padding: 0;
      border: 0;
      margin: 0;
      font-family: inherit;
      border-radius: 0;
      box-shadow: none;
      overflow: hidden;
    }

    .inp-paragraph,
    .pre {
      width: calc(100% - 30px);
      padding: 10px 15px;
      line-height: 24px;
      min-height: 90px;
      font-size: 14px;
      color: #333;
      border-radius: 4px;
      white-space: pre-wrap;
      word-wrap: break-word;
    }

    .comment_box_footer {
      display: flex;
      padding: 15px 0 50px;
      justify-content: space-between;
    }

    .comment_limit {
      display: inline-block;
      line-height: 28px;
      padding: 0 0 0 20px;
      font-size: 14px;
      color: #666;

      em {
        font-style: normal;
      }
    }
  }

  .comments_status {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 300px;

    .loading {
      font-size: 14px;
      line-height: 24px;
      color: #999;
    }

    .empty {
      text-align: center;

      p {
        font-size: 14px;
        line-height: 24px;
        color: #999;
      }

      .icon-empty-main {
        line-height: 140px;
        font-size: 100px;
        color: #bbb;
        opacity: 0.8;
      }
    }
  }

  .comment_item {
    position: relative;
    display: flex;
    padding: 20px;
    margin-bottom: 15px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
    border-radius: 6px;
    background: #fff;

    .comment_avatar {
      display: in;
      width: 40px;
      height: 40px;
      border-radius: 100%;
      overflow: hidden;
    }

    .comment_info {
      flex: 1;
      padding-left: 20px;

      a {
        display: inline-block;
        padding-bottom: 10px;
        line-height: 14px;
        color: #999;

        &:hover {
          color: var(--color-text);
        }
      }
    }

    .comment_content {
      line-height: 24px;
    }

    .comment_reply {
      padding: 10px;
      line-height: 28px;
      margin: 5px 0;
      color: #666;
      background: #fafafa;
      border-radius: 6px;
      box-shadow: 0 0px 27px rgb(0 0 0 / 6%) inset;

      a {
        display: inline-block;
        line-height: 28px;
        padding: 0;
      }
    }

    .comment_footer {
      display: flex;
      color: #999;

      .comment_time {
        display: inline-block;
        line-height: 28px;
      }

      .comment_oper {
        flex: 1;
        text-align: right;

        span {
          display: inline-block;
          margin-left: 15px;
          line-height: 28px;
          cursor: pointer;

          &.active,
          &.active .iconfont {
            color: var(--color-text);
          }
        }
      }
    }

    .isHot {
      position: absolute;
      right: 0;
      top: -8px;
      z-index: 1;
      // transform: rotate(45deg);

      .icon-choicest {
        font-size: 60px;
        color: var(--color-text);
        opacity: 0.8;
      }
    }

    .comment_del {
      cursor: pointer;
      display: none;
    }

    &:hover {
      .comment_del {
        display: inline-block;
      }
    }

    &:last-child {
      border: none;
    }
  }

  .pagination {
    padding: 30px 0;
    text-align: center;
  }

  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.3s;
  }
  .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
  }
</style>
